package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.repository.AttendanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    private Attendance testAttendance;
    private AttendanceDTO testAttendanceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Member member = new Member();
        member.setId(1L);

        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setId(1L);

        testAttendance = new Attendance(member, classSchedule, LocalDateTime.now());
        testAttendance.setId(1L);
        testAttendance.setAttended(true);

        testAttendanceDTO = new AttendanceDTO();
        testAttendanceDTO.setId(1L);
        testAttendanceDTO.setMemberId(1L);
        testAttendanceDTO.setClassScheduleId(1L);
        testAttendanceDTO.setAttendanceTime(LocalDateTime.now());
        testAttendanceDTO.setAttended(true);
    }

    @Test
    void testGetAllAttendances() {
        when(attendanceRepository.findAll()).thenReturn(Collections.singletonList(testAttendance));

        List<AttendanceDTO> result = attendanceService.getAllAttendances();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    void testGetAttendanceById() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(testAttendance));

        Optional<AttendanceDTO> result = Optional.ofNullable(attendanceService.getAttendanceById(1L));

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAttendanceById_NotFound() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AttendanceDTO> result = Optional.ofNullable(attendanceService.getAttendanceById(1L));

        assertFalse(result.isPresent());
        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void testAddAttendance() {
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(testAttendance);

        AttendanceDTO result = attendanceService.addAttendance(testAttendanceDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void testUpdateAttendance() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(testAttendance));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(testAttendance);

        testAttendanceDTO.setAttended(false);

        AttendanceDTO result = attendanceService.updateAttendance(1L, testAttendanceDTO);

        assertNotNull(result);
        assertFalse(result.isAttended());
        verify(attendanceRepository, times(1)).findById(1L);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void testUpdateAttendance_NotFound() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> attendanceService.updateAttendance(1L, testAttendanceDTO));

        assertEquals("Attendance not found with id 1", exception.getMessage());
        verify(attendanceRepository, times(1)).findById(1L);
        verify(attendanceRepository, times(0)).save(any(Attendance.class));
    }

    @Test
    void testDeleteAttendance() {
        when(attendanceRepository.existsById(1L)).thenReturn(true);

        boolean result = attendanceService.deleteAttendance(1L);

        assertTrue(result);
        verify(attendanceRepository, times(1)).existsById(1L);
        verify(attendanceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAttendance_NotFound() {
        when(attendanceRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> attendanceService.deleteAttendance(1L));

        assertEquals("Attendance not found with id 1", exception.getMessage());
        verify(attendanceRepository, times(1)).existsById(1L);
        verify(attendanceRepository, times(0)).deleteById(1L);
    }
}
