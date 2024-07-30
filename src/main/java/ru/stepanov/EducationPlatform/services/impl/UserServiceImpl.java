package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.mappers.*;
import ru.stepanov.EducationPlatform.models.Course;
import ru.stepanov.EducationPlatform.models.Enrolment;
import ru.stepanov.EducationPlatform.models.User;
import ru.stepanov.EducationPlatform.repositories.UserRepository;
import ru.stepanov.EducationPlatform.repositories.EnrolmentRepository;
import ru.stepanov.EducationPlatform.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

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
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmailAddress(userDto.getEmailAddress());
            user.setPassword(userDto.getPassword());
            user.setSignupDate(userDto.getSignupDate());
            user.setRole(RoleMapper.INSTANCE.toEntity(userDto.getRole()));
            user.setInstitution(InstitutionMapper.INSTANCE.toEntity(userDto.getInstitution()));
            user = userRepository.save(user);
            return UserMapper.INSTANCE.toDto(user);
        } else {
            return null; // или выбросить исключение
        }
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

