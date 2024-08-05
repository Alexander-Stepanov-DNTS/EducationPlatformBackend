package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.mappers.CourseMapper;
import ru.stepanov.EducationPlatform.mappers.UserMapper;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.models.Institution;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.InstitutionRepository;
import ru.stepanov.EducationPlatform.repositories.RoleRepository;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.repositories.EnrolmentRepository;
import ru.stepanov.EducationPlatform.services.UserService;
import ru.stepanov.EducationPlatform.exeptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EnrolmentRepository enrolmentRepository;

    private final RoleRepository roleRepository;

    private final InstitutionRepository institutionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EnrolmentRepository enrolmentRepository, RoleRepository roleRepository, InstitutionRepository institutionRepository) {
        this.userRepository = userRepository;
        this.enrolmentRepository = enrolmentRepository;
        this.roleRepository = roleRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setEmailAddress(userDto.getEmailAddress());
        user.setPassword(userDto.getPassword());
        user.setSignupDate(userDto.getSignupDate());
        user.setLogin(userDto.getLogin());

        Role role = roleRepository.findById(userDto.getRole().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRole(role);

        Institution institution = institutionRepository.findById(userDto.getInstitution().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));
        user.setInstitution(institution);

        user = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getCoursesByUserId(Long id) {
        List<Enrolment> enrollments = enrolmentRepository.findByStudentId(id);

        List<Course> courses = new ArrayList<>();
        for (Enrolment enrollment : enrollments) {
            courses.add(enrollment.getCourse());
        }

        return courses.stream()
                .map(CourseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}