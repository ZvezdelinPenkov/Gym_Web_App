package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.example.gym_web_app.util.ClassScheduleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassScheduleServiceTest {

    @InjectMocks
    private ClassScheduleService classScheduleService;

    @Mock
    private ClassScheduleRepository classScheduleRepository;

    private ClassSchedule testSchedule;
    private ClassScheduleDTO testScheduleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Class testClass = new Class();
        testClass.setId(1L);

        testSchedule = new ClassSchedule(testClass, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0));
        testSchedule.setId(1L);

        testScheduleDTO = new ClassScheduleDTO();
        testScheduleDTO.setId(1L);
        testScheduleDTO.setClassId(1L);
        testScheduleDTO.setDate(LocalDate.now());
        testScheduleDTO.setStartTime(LocalTime.of(10, 0));
        testScheduleDTO.setEndTime(LocalTime.of(11, 0));
    }

    @Test
    void testGetAllSchedules() {
        when(classScheduleRepository.findAll()).thenReturn(Arrays.asList(testSchedule));

        List<ClassScheduleDTO> result = classScheduleService.getAllSchedules();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(LocalDate.now(), result.get(0).getDate());
        verify(classScheduleRepository, times(1)).findAll();
    }

    @Test
    void testGetScheduleById() {
        when(classScheduleRepository.findById(1L)).thenReturn(Optional.of(testSchedule));

        Optional<ClassScheduleDTO> result = classScheduleService.getScheduleById(1L);

        assertTrue(result.isPresent());
        assertEquals(LocalTime.of(10, 0), result.get().getStartTime());
        verify(classScheduleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetScheduleById_NotFound() {
        when(classScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ClassScheduleDTO> result = classScheduleService.getScheduleById(1L);

        assertFalse(result.isPresent());
        verify(classScheduleRepository, times(1)).findById(1L);
    }

    @Test
    void testAddSchedule() {
        when(classScheduleRepository.save(any(ClassSchedule.class))).thenReturn(testSchedule);

        ClassScheduleDTO result = classScheduleService.addSchedule(testScheduleDTO);

        assertNotNull(result);
        assertEquals(LocalTime.of(10, 0), result.getStartTime());
        verify(classScheduleRepository, times(1)).save(any(ClassSchedule.class));
    }

    @Test
    void testUpdateSchedule() {
        when(classScheduleRepository.findById(1L)).thenReturn(Optional.of(testSchedule));
        when(classScheduleRepository.save(any(ClassSchedule.class))).thenReturn(testSchedule);

        testScheduleDTO.setStartTime(LocalTime.of(9, 0));

        ClassScheduleDTO result = classScheduleService.updateSchedule(1L, testScheduleDTO);

        assertNotNull(result);
        assertEquals(LocalTime.of(9, 0), result.getStartTime());
        verify(classScheduleRepository, times(1)).findById(1L);
        verify(classScheduleRepository, times(1)).save(any(ClassSchedule.class));
    }

    @Test
    void testUpdateSchedule_NotFound() {
        when(classScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> classScheduleService.updateSchedule(1L, testScheduleDTO));

        assertEquals("ClassSchedule not found with id 1", exception.getMessage());
        verify(classScheduleRepository, times(1)).findById(1L);
        verify(classScheduleRepository, times(0)).save(any(ClassSchedule.class));
    }

    @Test
    void testDeleteSchedule() {
        when(classScheduleRepository.existsById(1L)).thenReturn(true);

        boolean result = classScheduleService.deleteSchedule(1L);

        assertTrue(result);
        verify(classScheduleRepository, times(1)).existsById(1L);
        verify(classScheduleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSchedule_NotFound() {
        when(classScheduleRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> classScheduleService.deleteSchedule(1L));

        assertEquals("ClassSchedule not found with id 1", exception.getMessage());
        verify(classScheduleRepository, times(1)).existsById(1L);
        verify(classScheduleRepository, times(0)).deleteById(1L);
    }
}
