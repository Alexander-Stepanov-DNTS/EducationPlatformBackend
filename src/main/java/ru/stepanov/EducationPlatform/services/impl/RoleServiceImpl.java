package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.RoleDto;
import ru.stepanov.EducationPlatform.mappers.RoleMapper;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.repositories.RoleRepository;
import ru.stepanov.EducationPlatform.services.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public RoleDto getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(RoleMapper.INSTANCE::toDto).orElse(null);
    }

    public RoleDto getRoleByName(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        return role.map(RoleMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.INSTANCE.toEntity(roleDto);
        role = roleRepository.save(role);
        return RoleMapper.INSTANCE.toDto(role);
    }

    @Override
    @Transactional
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setName(roleDto.getName());
            role = roleRepository.save(role);
            return RoleMapper.INSTANCE.toDto(role);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

