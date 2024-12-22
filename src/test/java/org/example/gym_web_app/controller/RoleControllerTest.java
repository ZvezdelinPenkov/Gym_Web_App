package org.example.gym_web_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gym_web_app.dto.RoleDTO;
import org.example.gym_web_app.security.JwtRequestFilter;
import org.example.gym_web_app.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper; // Used to serialize and deserialize JSON

    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("Admin");
    }

    @Test
    void testGetAllRoles() throws Exception {
        // Mock service response
        when(roleService.getAllRoles()).thenReturn(List.of(roleDTO));

        // Perform GET request
        mockMvc.perform(get("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Admin"));

        // Verify service interaction
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void testGetRoleById() throws Exception {
        // Mock service response with a RoleDTO
        when(roleService.getRoleById(1L)).thenReturn(roleDTO); // Directly return RoleDTO

        // Perform GET request
        mockMvc.perform(get("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Admin"));

        // Verify service interaction
        verify(roleService, times(1)).getRoleById(1L);
    }

    @Test
    void testGetRoleById_NotFound() throws Exception {
        // Mock service response to return null
        when(roleService.getRoleById(1L)).thenReturn(null); // Return null to simulate not found

        // Perform GET request
        mockMvc.perform(get("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Verify service interaction
        verify(roleService, times(1)).getRoleById(1L);
    }

    @Test
    void testAddRole() throws Exception {
        // Mock service response
        when(roleService.addRole(any(RoleDTO.class))).thenReturn(roleDTO);

        // Perform POST request
        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Admin"));

        // Verify service interaction
        verify(roleService, times(1)).addRole(any(RoleDTO.class));
    }

    @Test
    void testAddRole_BadRequest() throws Exception {
        // Mock service response to throw an exception
        when(roleService.addRole(any(RoleDTO.class))).thenThrow(IllegalArgumentException.class);

        // Perform POST request
        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isBadRequest());

        // Verify service interaction
        verify(roleService, times(1)).addRole(any(RoleDTO.class));
    }

    @Test
    void testUpdateRole() throws Exception {
        // Mock service response
        when(roleService.updateRole(eq(1L), any(RoleDTO.class))).thenReturn(roleDTO);

        // Perform PUT request
        mockMvc.perform(put("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Admin"));

        // Verify service interaction
        verify(roleService, times(1)).updateRole(eq(1L), any(RoleDTO.class));
    }

    @Test
    void testUpdateRole_NotFound() throws Exception {
        // Mock service response to throw exception
        when(roleService.updateRole(eq(1L), any(RoleDTO.class))).thenThrow(new RuntimeException("Role not found"));

        // Perform PUT request
        mockMvc.perform(put("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isNotFound());

        // Verify service interaction
        verify(roleService, times(1)).updateRole(eq(1L), any(RoleDTO.class));
    }

    @Test
    void testDeleteRole() throws Exception {
        // Mock service response
        when(roleService.deleteRole(1L)).thenReturn(true);

        // Perform DELETE request
        mockMvc.perform(delete("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify service interaction
        verify(roleService, times(1)).deleteRole(1L);
    }

    @Test
    void testDeleteRole_NotFound() throws Exception {
        // Mock service response to throw exception
        doThrow(new RuntimeException("Role not found")).when(roleService).deleteRole(1L);

        // Perform DELETE request
        mockMvc.perform(delete("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Verify service interaction
        verify(roleService, times(1)).deleteRole(1L);
    }
}
