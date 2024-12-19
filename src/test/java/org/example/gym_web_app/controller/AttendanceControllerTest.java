package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttendanceControllerTest {

    @InjectMocks
    private AttendanceController attendanceController;

    @Mock
    private AttendanceService attendanceService;

    private AttendanceDTO testAttendanceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAttendanceDTO = new AttendanceDTO();
        testAttendanceDTO.setId(1L);
        testAttendanceDTO.setMemberId(1L);
        testAttendanceDTO.setClassScheduleId(1L);
        testAttendanceDTO.setAttendanceTime(LocalDateTime.now());
        testAttendanceDTO.setAttended(true);
    }

    @Test
    void testGetAllAttendances() {
        when(attendanceService.getAllAttendances()).thenReturn(Collections.singletonList(testAttendanceDTO));

        ResponseEntity<List<AttendanceDTO>> response = attendanceController.getAllAttendances();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().getFirst().getId());
        verify(attendanceService, times(1)).getAllAttendances();
    }

    @Test
    void testGetAttendanceById() {
        // Mock service response to directly return the testAttendanceDTO
        when(attendanceService.getAttendanceById(1L)).thenReturn(testAttendanceDTO);

        // Perform the request
        ResponseEntity<AttendanceDTO> response = attendanceController.getAttendanceById(1L);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());

        // Verify interaction with the mock
        verify(attendanceService, times(1)).getAttendanceById(1L);
    }

    @Test
    void testGetAttendanceById_NotFound() {
        // Mock service response to return null
        when(attendanceService.getAttendanceById(1L)).thenReturn(null);

        // Perform the request
        ResponseEntity<AttendanceDTO> response = attendanceController.getAttendanceById(1L);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify interaction with the mock
        verify(attendanceService, times(1)).getAttendanceById(1L);
    }

    @Test
    void testAddAttendance() {
        when(attendanceService.addAttendance(any(AttendanceDTO.class))).thenReturn(testAttendanceDTO);

        ResponseEntity<AttendanceDTO> response = attendanceController.addAttendance(testAttendanceDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(attendanceService, times(1)).addAttendance(any(AttendanceDTO.class));
    }

    @Test
    void testAddAttendance_BadRequest() {
        when(attendanceService.addAttendance(any(AttendanceDTO.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<AttendanceDTO> response = attendanceController.addAttendance(testAttendanceDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(attendanceService, times(1)).addAttendance(any(AttendanceDTO.class));
    }

    @Test
    void testUpdateAttendance() {
        when(attendanceService.updateAttendance(eq(1L), any(AttendanceDTO.class))).thenReturn(testAttendanceDTO);

        ResponseEntity<AttendanceDTO> response = attendanceController.updateAttendance(1L, testAttendanceDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(attendanceService, times(1)).updateAttendance(eq(1L), any(AttendanceDTO.class));
    }

    @Test
    void testUpdateAttendance_NotFound() {
        when(attendanceService.updateAttendance(eq(1L), any(AttendanceDTO.class))).thenThrow(new RuntimeException("Attendance not found"));

        ResponseEntity<AttendanceDTO> response = attendanceController.updateAttendance(1L, testAttendanceDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(attendanceService, times(1)).updateAttendance(eq(1L), any(AttendanceDTO.class));
    }

    @Test
    void testDeleteAttendance() {
        when(attendanceService.deleteAttendance(1L)).thenReturn(true);

        ResponseEntity<Void> response = attendanceController.deleteAttendance(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(attendanceService, times(1)).deleteAttendance(1L);
    }

    @Test
    void testDeleteAttendance_NotFound() {
        doThrow(new RuntimeException("Attendance not found"))
                .when(attendanceService).deleteAttendance(1L);

        ResponseEntity<Void> response = attendanceController.deleteAttendance(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(attendanceService, times(1)).deleteAttendance(1L);
    }
}