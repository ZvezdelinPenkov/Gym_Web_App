package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private UsersDTO testUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUserDTO = new UsersDTO();
        testUserDTO.setId(1L);
        testUserDTO.setUsername("testuser");
        testUserDTO.setEmail("test@example.com");
        testUserDTO.setPassword("password123");
    }

    @Test
    void testGetAllUsers() {
        when(usersService.getAllUsers()).thenReturn(Collections.singletonList(testUserDTO));

        ResponseEntity<List<UsersDTO>> response = usersController.getAllUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("testuser", response.getBody().getFirst().getUsername());
        verify(usersService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Found() {
        when(usersService.getUserById(1L)).thenReturn(testUserDTO); // Directly return UsersDTO

        ResponseEntity<UsersDTO> response = usersController.getUserById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
        verify(usersService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(usersService.getUserById(1L)).thenReturn(null); // Return null to simulate not found

        ResponseEntity<UsersDTO> response = usersController.getUserById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(usersService, times(1)).getUserById(1L);
    }

    @Test
    void testAddUser_Success() {
        when(usersService.addUser(any(UsersDTO.class))).thenReturn(testUserDTO);

        ResponseEntity<UsersDTO> response = usersController.addUser(testUserDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
        verify(usersService, times(1)).addUser(any(UsersDTO.class));
    }

    @Test
    void testAddUser_BadRequest() {
        when(usersService.addUser(any(UsersDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<UsersDTO> response = usersController.addUser(testUserDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(usersService, times(1)).addUser(any(UsersDTO.class));
    }

    @Test
    void testUpdateUser_Success() {
        when(usersService.updateUser(eq(1L), any(UsersDTO.class))).thenReturn(testUserDTO);

        ResponseEntity<UsersDTO> response = usersController.updateUser(1L, testUserDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
        verify(usersService, times(1)).updateUser(eq(1L), any(UsersDTO.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(usersService.updateUser(eq(1L), any(UsersDTO.class))).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<UsersDTO> response = usersController.updateUser(1L, testUserDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(usersService, times(1)).updateUser(eq(1L), any(UsersDTO.class));
    }

    @Test
    void testDeleteUser_Success() {
        when(usersService.deleteUser(1L)).thenReturn(true);

        ResponseEntity<Void> response = usersController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usersService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        doThrow(new RuntimeException("User not found"))
                .when(usersService).deleteUser(1L);

        ResponseEntity<Void> response = usersController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usersService, times(1)).deleteUser(1L);
    }
}
