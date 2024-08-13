package ru.stepanov.EducationPlatform.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.stepanov.EducationPlatform.security.userDetails.MyUserDetails;
import ru.stepanov.EducationPlatform.security.userDetails.MyUserDetailsService;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService myUserDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(MyUserDetailsService myUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = null;
        String username = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    jwtToken = cookie.getValue();
                    try {
                        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to get JWT Token");
                        return;
                    } catch (ExpiredJwtException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
                        return;
                    }
                    break;
                }
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            MyUserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                request.setAttribute("id", jwtTokenUtil.getUserIdFromToken(jwtToken));
                request.setAttribute("login", jwtTokenUtil.getUserLoginFromToken(jwtToken));
                request.setAttribute("role", jwtTokenUtil.getUserRoleFromToken(jwtToken));
            }
        }
        filterChain.doFilter(request, response);
    }
}