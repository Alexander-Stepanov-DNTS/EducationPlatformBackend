package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.RoleDto;
import ru.stepanov.EducationPlatform.models.Role;
import ru.stepanov.EducationPlatform.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    private Role savedRole;

    @BeforeEach
    public void setUp() {
        roleRepository.deleteAll();

        Role role = new Role();
        role.setName("Test Role");
        savedRole = roleRepository.save(role);
    }

    @Test
    @Transactional
    public void testGetRoleById() {
        RoleDto foundRole = roleService.getRoleById(savedRole.getId());
        assertNotNull(foundRole);
        assertEquals(savedRole.getId(), foundRole.getId());
        assertEquals(savedRole.getName(), foundRole.getName());
    }

    @Test
    @Transactional
    public void testGetRoleByName() {
        RoleDto foundRole = roleService.getRoleByName(savedRole.getName());
        assertNotNull(foundRole);
        assertEquals(savedRole.getId(), foundRole.getId());
        assertEquals(savedRole.getName(), foundRole.getName());
    }

    @Test
    @Transactional
    public void testGetAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        assertFalse(roles.isEmpty());
        assertEquals(1, roles.size());
        assertEquals(savedRole.getId(), roles.get(0).getId());
    }

    @Test
    @Transactional
    public void testCreateRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("New Role");

        RoleDto createdRole = roleService.createRole(roleDto);
        assertNotNull(createdRole.getId());
        assertEquals(roleDto.getName(), createdRole.getName());
    }

    @Test
    @Transactional
    public void testUpdateRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("Updated Role");

        RoleDto updatedRole = roleService.updateRole(savedRole.getId(), roleDto);
        assertNotNull(updatedRole);
        assertEquals(savedRole.getId(), updatedRole.getId());
        assertEquals(roleDto.getName(), updatedRole.getName());
    }

    @Test
    @Transactional
    public void testDeleteRole() {
        roleService.deleteRole(savedRole.getId());
        Optional<Role> deletedRole = roleRepository.findById(savedRole.getId());
        assertFalse(deletedRole.isPresent());
    }
}