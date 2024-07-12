package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto getRoleById(Long id);
    List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(Long id, RoleDto roleDto);
    void deleteRole(Long id);
}

