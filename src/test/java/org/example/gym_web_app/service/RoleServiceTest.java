package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.RoleDTO;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.repository.RoleRepository;
import org.example.gym_web_app.util.RoleMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    public RoleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Setup mock data
        Role role = new Role("Admin");
        role.setId(1L);
        when(roleRepository.findAll()).thenReturn(List.of(role));

        // Execute service call
        List<RoleDTO> roles = roleService.getAllRoles();

        // Verify and Assert
        assertEquals(1, roles.size());
        assertEquals("Admin", roles.get(0).getName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoleById() {
        // Setup mock data
        Role role = new Role("User");
        role.setId(2L);
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        // Execute service call
        Optional<RoleDTO> roleDTO = roleService.getRoleById(2L);

        // Verify and Assert
        assertTrue(roleDTO.isPresent());
        assertEquals("User", roleDTO.get().getName());
        verify(roleRepository, times(1)).findById(2L);
    }

    @Test
    void testAddRole() {
        // Setup mock data
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("Manager");

        Role role = RoleMapper.toEntity(roleDTO);
        role.setId(3L);

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Execute service call
        RoleDTO savedRole = roleService.addRole(roleDTO);

        // Verify and Assert
        assertEquals("Manager", savedRole.getName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testUpdateRole() {
        // Setup mock data
        Role existingRole = new Role("Employee");
        existingRole.setId(4L);

        RoleDTO updatedRoleDTO = new RoleDTO();
        updatedRoleDTO.setName("Team Lead");

        when(roleRepository.findById(4L)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(any(Role.class))).thenReturn(existingRole);

        // Execute service call
        RoleDTO updatedRole = roleService.updateRole(4L, updatedRoleDTO);

        // Verify and Assert
        assertEquals("Team Lead", updatedRole.getName());
        verify(roleRepository, times(1)).findById(4L);
        verify(roleRepository, times(1)).save(existingRole);
    }

    @Test
    void testDeleteRole() {
        // Setup mock data
        when(roleRepository.existsById(5L)).thenReturn(true);

        // Execute service call
        boolean result = roleService.deleteRole(5L);

        // Verify and Assert
        assertTrue(result);
        verify(roleRepository, times(1)).existsById(5L);
        verify(roleRepository, times(1)).deleteById(5L);
    }
}
