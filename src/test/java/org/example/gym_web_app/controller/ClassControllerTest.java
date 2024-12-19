package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.service.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassControllerTest {

    @InjectMocks
    private ClassController classController;

    @Mock
    private ClassService classService;

    private ClassDTO testClassDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testClassDTO = new ClassDTO();
        testClassDTO.setId(1L);
        testClassDTO.setTitle("Yoga");
        testClassDTO.setDuration(60);
        testClassDTO.setMaxParticipants(20);
        testClassDTO.setInstructorId(1L);
    }

    @Test
    void testGetAllClasses() {
        when(classService.getAllClasses()).thenReturn(Arrays.asList(testClassDTO));

        ResponseEntity<List<ClassDTO>> response = classController.getAllClasses();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Yoga", response.getBody().get(0).getTitle());
        verify(classService, times(1)).getAllClasses();
    }

    @Test
    void testGetClassById() {
        // Mock service response to return a ClassDTO
        when(classService.getClassById(1L)).thenReturn(testClassDTO);

        // Perform the request
        ResponseEntity<ClassDTO> response = classController.getClassById(1L);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Yoga", response.getBody().getTitle());

        // Verify interaction with the mock
        verify(classService, times(1)).getClassById(1L);
    }

    @Test
    void testGetClassById_NotFound() {
        // Mock service response to return null
        when(classService.getClassById(1L)).thenReturn(null);

        // Perform the request
        ResponseEntity<ClassDTO> response = classController.getClassById(1L);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify interaction with the mock
        verify(classService, times(1)).getClassById(1L);
    }

    @Test
    void testAddClass() {
        when(classService.addClass(any(ClassDTO.class))).thenReturn(testClassDTO);

        ResponseEntity<ClassDTO> response = classController.addClass(testClassDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Yoga", response.getBody().getTitle());
        verify(classService, times(1)).addClass(any(ClassDTO.class));
    }

    @Test
    void testAddClass_BadRequest() {
        when(classService.addClass(any(ClassDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<ClassDTO> response = classController.addClass(testClassDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(classService, times(1)).addClass(any(ClassDTO.class));
    }

    @Test
    void testUpdateClass() {
        when(classService.updateClass(eq(1L), any(ClassDTO.class))).thenReturn(testClassDTO);

        ResponseEntity<ClassDTO> response = classController.updateClass(1L, testClassDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Yoga", response.getBody().getTitle());
        verify(classService, times(1)).updateClass(eq(1L), any(ClassDTO.class));
    }

    @Test
    void testUpdateClass_NotFound() {
        when(classService.updateClass(eq(1L), any(ClassDTO.class))).thenThrow(new RuntimeException("Class not found"));

        ResponseEntity<ClassDTO> response = classController.updateClass(1L, testClassDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(classService, times(1)).updateClass(eq(1L), any(ClassDTO.class));
    }

    @Test
    void testDeleteClass() {
        when(classService.deleteClass(1L)).thenReturn(true);

        ResponseEntity<Void> response = classController.deleteClass(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(classService, times(1)).deleteClass(1L);
    }

    @Test
    void testDeleteClass_NotFound() {
        doThrow(new RuntimeException("Class not found"))
                .when(classService).deleteClass(1L);

        ResponseEntity<Void> response = classController.deleteClass(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(classService, times(1)).deleteClass(1L);
    }
}
