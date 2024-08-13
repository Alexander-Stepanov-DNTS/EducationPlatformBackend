package ru.stepanov.EducationPlatform.security.userDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.InstitutionRepository;
import ru.stepanov.EducationPlatform.repositories.RoleRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.services.impl.InstitutionServiceImpl;
import ru.stepanov.EducationPlatform.services.impl.RoleServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MyUserDetailsService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final InstitutionRepository institutionRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, InstitutionRepository institutionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailAddress(emailAddress);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MyUserDetails(user);
    }

    public boolean existsByEmail(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).isPresent();
    }

    public MyUserDetails saveUser(String login, String password, String email_address) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setEmailAddress(email_address);
        newUser.setPassword(password);

        Optional<Role> roleOptional = roleRepository.findByName("Студент");
        Optional<Institution> institutionOptional = institutionRepository.findById(1L);

        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found"));
        Institution institution = institutionOptional.orElseThrow(() -> new RuntimeException("Institution not found"));

        newUser.setRole(role);
        newUser.setInstitution(institution);
        newUser.setSignupDate(LocalDate.from(LocalDateTime.now()));

        return new MyUserDetails(userRepository.save(newUser));
    }
}