package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.service.ClassScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassScheduleControllerTest {

    @InjectMocks
    private ClassScheduleController classScheduleController;

    @Mock
    private ClassScheduleService classScheduleService;

    private ClassScheduleDTO testScheduleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testScheduleDTO = new ClassScheduleDTO();
        testScheduleDTO.setId(1L);
        testScheduleDTO.setClassId(1L);
        testScheduleDTO.setDate(LocalDate.now());
        testScheduleDTO.setStartTime(LocalTime.of(10, 0));
        testScheduleDTO.setEndTime(LocalTime.of(11, 0));
    }

    @Test
    void testGetAllSchedules() {
        when(classScheduleService.getAllSchedules()).thenReturn(Arrays.asList(testScheduleDTO));

        ResponseEntity<List<ClassScheduleDTO>> response = classScheduleController.getAllSchedules();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(LocalDate.now(), response.getBody().get(0).getDate());
        verify(classScheduleService, times(1)).getAllSchedules();
    }

    @Test
    void testGetScheduleById() {
        when(classScheduleService.getScheduleById(1L)).thenReturn(Optional.of(testScheduleDTO));

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.getScheduleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(LocalTime.of(10, 0), response.getBody().getStartTime());
        verify(classScheduleService, times(1)).getScheduleById(1L);
    }

    @Test
    void testGetScheduleById_NotFound() {
        when(classScheduleService.getScheduleById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.getScheduleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(classScheduleService, times(1)).getScheduleById(1L);
    }

    @Test
    void testAddSchedule() {
        when(classScheduleService.addSchedule(any(ClassScheduleDTO.class))).thenReturn(testScheduleDTO);

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.addSchedule(testScheduleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(LocalTime.of(10, 0), response.getBody().getStartTime());
        verify(classScheduleService, times(1)).addSchedule(any(ClassScheduleDTO.class));
    }

    @Test
    void testAddSchedule_BadRequest() {
        when(classScheduleService.addSchedule(any(ClassScheduleDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.addSchedule(testScheduleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(classScheduleService, times(1)).addSchedule(any(ClassScheduleDTO.class));
    }

    @Test
    void testUpdateSchedule() {
        when(classScheduleService.updateSchedule(eq(1L), any(ClassScheduleDTO.class))).thenReturn(testScheduleDTO);

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.updateSchedule(1L, testScheduleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(LocalTime.of(10, 0), response.getBody().getStartTime());
        verify(classScheduleService, times(1)).updateSchedule(eq(1L), any(ClassScheduleDTO.class));
    }

    @Test
    void testUpdateSchedule_NotFound() {
        when(classScheduleService.updateSchedule(eq(1L), any(ClassScheduleDTO.class))).thenThrow(new RuntimeException("ClassSchedule not found"));

        ResponseEntity<ClassScheduleDTO> response = classScheduleController.updateSchedule(1L, testScheduleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(classScheduleService, times(1)).updateSchedule(eq(1L), any(ClassScheduleDTO.class));
    }

    @Test
    void testDeleteSchedule() {
        when(classScheduleService.deleteSchedule(1L)).thenReturn(true);

        ResponseEntity<Void> response = classScheduleController.deleteSchedule(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(classScheduleService, times(1)).deleteSchedule(1L);
    }

    @Test
    void testDeleteSchedule_NotFound() {
        doThrow(new RuntimeException("ClassSchedule not found"))
                .when(classScheduleService).deleteSchedule(1L);

        ResponseEntity<Void> response = classScheduleController.deleteSchedule(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(classScheduleService, times(1)).deleteSchedule(1L);
    }
}
