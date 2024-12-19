package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassServiceTest {

    @InjectMocks
    private ClassService classService;

    @Mock
    private ClassRepository classRepository;

    private Class testClass;
    private ClassDTO testClassDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Users instructor = new Users();
        instructor.setId(1L);

        testClass = new Class("Yoga", 60, 20, instructor);
        testClass.setId(1L);

        testClassDTO = new ClassDTO();
        testClassDTO.setId(1L);
        testClassDTO.setTitle("Yoga");
        testClassDTO.setDuration(60);
        testClassDTO.setMaxParticipants(20);
        testClassDTO.setInstructorId(1L);
    }

    @Test
    void testGetAllClasses() {
        when(classRepository.findAll()).thenReturn(Collections.singletonList(testClass));

        List<ClassDTO> result = classService.getAllClasses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.getFirst().getTitle());
        verify(classRepository, times(1)).findAll();
    }

    @Test
    void testGetClassById() {
        when(classRepository.findById(1L)).thenReturn(Optional.of(testClass));

        Optional<ClassDTO> result = Optional.ofNullable(classService.getClassById(1L));

        assertTrue(result.isPresent());
        assertEquals("Yoga", result.get().getTitle());
        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClassById_NotFound() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ClassDTO> result = Optional.ofNullable(classService.getClassById(1L));

        assertFalse(result.isPresent());
        verify(classRepository, times(1)).findById(1L);
    }

    @Test
    void testAddClass() {
        when(classRepository.save(any(Class.class))).thenReturn(testClass);

        ClassDTO result = classService.addClass(testClassDTO);

        assertNotNull(result);
        assertEquals("Yoga", result.getTitle());
        verify(classRepository, times(1)).save(any(Class.class));
    }

    @Test
    void testUpdateClass() {
        when(classRepository.findById(1L)).thenReturn(Optional.of(testClass));
        when(classRepository.save(any(Class.class))).thenReturn(testClass);

        ClassDTO updatedDTO = new ClassDTO();
        updatedDTO.setTitle("Advanced Yoga");
        updatedDTO.setDuration(90);
        updatedDTO.setMaxParticipants(25);

        ClassDTO result = classService.updateClass(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Advanced Yoga", result.getTitle());
        assertEquals(90, result.getDuration());
        verify(classRepository, times(1)).findById(1L);
        verify(classRepository, times(1)).save(any(Class.class));
    }

    @Test
    void testUpdateClass_NotFound() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> classService.updateClass(1L, testClassDTO));

        assertEquals("Class not found with id 1", exception.getMessage());
        verify(classRepository, times(1)).findById(1L);
        verify(classRepository, times(0)).save(any(Class.class));
    }

    @Test
    void testDeleteClass() {
        when(classRepository.existsById(1L)).thenReturn(true);

        boolean result = classService.deleteClass(1L);

        assertTrue(result);
        verify(classRepository, times(1)).existsById(1L);
        verify(classRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClass_NotFound() {
        when(classRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> classService.deleteClass(1L));

        assertEquals("Class not found with id 1", exception.getMessage());
        verify(classRepository, times(1)).existsById(1L);
        verify(classRepository, times(0)).deleteById(1L);
    }
}