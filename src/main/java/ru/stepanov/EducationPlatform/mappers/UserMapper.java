package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.UserDto;
import ru.stepanov.EducationPlatform.models.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
