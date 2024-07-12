package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.RoleDto;
import ru.stepanov.EducationPlatform.models.Role;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}
