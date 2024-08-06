package ru.stepanov.EducationPlatform.security.userDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.stepanov.EducationPlatform.DTO.InstitutionDto;
import ru.stepanov.EducationPlatform.DTO.RoleDto;
import ru.stepanov.EducationPlatform.mappers.InstitutionMapper;
import ru.stepanov.EducationPlatform.mappers.RoleMapper;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.services.impl.InstitutionServiceImpl;
import ru.stepanov.EducationPlatform.services.impl.RoleServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MyUserDetailsService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    private final RoleServiceImpl roleService;

    private final InstitutionServiceImpl institutionService;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository, RoleServiceImpl roleService, InstitutionServiceImpl institutionService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.institutionService = institutionService;
    }

    @Override
    public MyUserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(user);
    }

    public boolean existsByEmail(String emailAddress) {
        User user = userRepository.findByEmailAddress(emailAddress);
        return user != null;
    }

    public MyUserDetails saveUser(String login, String password, String email_address) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setEmailAddress(email_address);
        newUser.setPassword(password);
        RoleDto studentRole = roleService.getRoleByName("Студент");
        InstitutionDto institutionDto = institutionService.getInstitutionById(1L);
        System.out.println(institutionDto.getName());
        newUser.setRole(RoleMapper.INSTANCE.toEntity(studentRole));
        newUser.setInstitution(InstitutionMapper.INSTANCE.toEntity(institutionDto));
        newUser.setSignupDate(LocalDate.from(LocalDateTime.now()));

        return new MyUserDetails(userRepository.save(newUser));
    }
}