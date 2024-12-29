package org.example.gym_web_app.service;

import jakarta.transaction.Transactional;
import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.exception.InvalidRequestException;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.repository.ClassRepository;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.example.gym_web_app.util.ClassScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;
    @Autowired
    private ClassRepository classRepository;

    public List<ClassScheduleDTO> getAllSchedules() {
        return classScheduleRepository.findAll()
                .stream()
                .map(ClassScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClassScheduleDTO getScheduleById(Long id) {
        ClassSchedule schedule = classScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClassSchedule not found with id: " + id));
        return ClassScheduleMapper.toDTO(schedule);
    }

    public ClassScheduleDTO addSchedule(ClassScheduleDTO scheduleDTO) {
        validateScheduleInput(scheduleDTO);

        ClassSchedule schedule = ClassScheduleMapper.toEntity(scheduleDTO, classRepository);
        ClassSchedule savedSchedule = classScheduleRepository.save(schedule);

        return ClassScheduleMapper.toDTO(savedSchedule);
    }

    public ClassScheduleDTO updateSchedule(Long id, ClassScheduleDTO scheduleDTO) {
        validateScheduleInput(scheduleDTO);

        ClassSchedule existingSchedule = classScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClassSchedule not found with id: " + id));

        existingSchedule.setDate(scheduleDTO.getDate());
        existingSchedule.setStartTime(scheduleDTO.getStartTime());
        existingSchedule.setEndTime(scheduleDTO.getEndTime());
        ClassSchedule updatedSchedule = classScheduleRepository.save(existingSchedule);

        return ClassScheduleMapper.toDTO(updatedSchedule);
    }

    public boolean deleteSchedule(Long id) {
        if (!classScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("ClassSchedule not found with id: " + id);
        }
        classScheduleRepository.deleteById(id);
        return true;
    }

    private void validateScheduleInput(ClassScheduleDTO scheduleDTO) {
        if (scheduleDTO.getDate() == null) {
            throw new InvalidRequestException("Schedule date cannot be null");
        }
        if (scheduleDTO.getStartTime() == null || scheduleDTO.getEndTime() == null) {
            throw new InvalidRequestException("Schedule start and end times cannot be null");
        }
        if (scheduleDTO.getStartTime().isAfter(scheduleDTO.getEndTime())) {
            throw new InvalidRequestException("Schedule start time must be before end time");
        }
    }
}
