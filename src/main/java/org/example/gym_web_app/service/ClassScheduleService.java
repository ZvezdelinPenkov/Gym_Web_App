package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.example.gym_web_app.util.ClassScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    public List<ClassScheduleDTO> getAllSchedules() {
        return classScheduleRepository.findAll()
                .stream()
                .map(ClassScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClassScheduleDTO> getScheduleById(Long id) {
        return classScheduleRepository.findById(id).map(ClassScheduleMapper::toDTO);
    }

    public ClassScheduleDTO addSchedule(ClassScheduleDTO scheduleDTO) {
        ClassSchedule schedule = ClassScheduleMapper.toEntity(scheduleDTO);
        // Fetch and set the associated Class entity in the service layer (if needed)
        ClassSchedule savedSchedule = classScheduleRepository.save(schedule);
        return ClassScheduleMapper.toDTO(savedSchedule);
    }

    public ClassScheduleDTO updateSchedule(Long id, ClassScheduleDTO scheduleDTO) {
        Optional<ClassSchedule> optionalSchedule = classScheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new RuntimeException("ClassSchedule not found with id " + id);
        }

        ClassSchedule schedule = optionalSchedule.get();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        // Fetch and set the associated Class entity in the service layer (if needed)
        ClassSchedule updatedSchedule = classScheduleRepository.save(schedule);
        return ClassScheduleMapper.toDTO(updatedSchedule);
    }

    public boolean deleteSchedule(Long id) {
        if (classScheduleRepository.existsById(id)) {
            classScheduleRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("ClassSchedule not found with id " + id);
        }
    }
}
