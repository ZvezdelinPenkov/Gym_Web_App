package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Users testUser;
    private UsersDTO testUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new Users();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");

        testUserDTO = new UsersDTO();
        testUserDTO.setId(1L);
        testUserDTO.setUsername("testuser");
        testUserDTO.setEmail("test@example.com");
        testUserDTO.setPassword("password123");
    }

    @Test
    void testGetAllUsers() {
        when(usersRepository.findAll()).thenReturn(Collections.singletonList(testUser));

        List<UsersDTO> result = usersService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.getFirst().getUsername());
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<UsersDTO> result = Optional.ofNullable(usersService.getUserById(1L));

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UsersDTO> result = Optional.ofNullable(usersService.getUserById(1L));

        assertFalse(result.isPresent());
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUser() {
        when(usersRepository.save(any(Users.class))).thenReturn(testUser);

        UsersDTO result = usersService.addUser(testUserDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testUpdateUser() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(usersRepository.save(any(Users.class))).thenReturn(testUser);

        testUserDTO.setUsername("updateduser");

        UsersDTO result = usersService.updateUser(1L, testUserDTO);

        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        verify(usersRepository, times(1)).findById(1L);
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usersService.updateUser(1L, testUserDTO));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(usersRepository, times(1)).findById(1L);
        verify(usersRepository, times(0)).save(any(Users.class));
    }

    @Test
    void testDeleteUser() {
        when(usersRepository.existsById(1L)).thenReturn(true);

        boolean result = usersService.deleteUser(1L);

        assertTrue(result);
        verify(usersRepository, times(1)).existsById(1L);
        verify(usersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(usersRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usersService.deleteUser(1L));

        assertEquals("User not found with id 1", exception.getMessage());
        verify(usersRepository, times(1)).existsById(1L);
        verify(usersRepository, times(0)).deleteById(1L);
    }
}