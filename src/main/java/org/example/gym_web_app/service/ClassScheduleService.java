package org.example.gym_web_app.service;

import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    public List<ClassSchedule> getAllSchedules() {
        return classScheduleRepository.findAll();
    }

    public Optional<ClassSchedule> getScheduleById(Long id) {
        return classScheduleRepository.findById(id);
    }

    public ClassSchedule addSchedule(ClassSchedule schedule) {
        validateSchedule(schedule);
        return classScheduleRepository.save(schedule);
    }

    public ClassSchedule updateSchedule(Long id, ClassSchedule scheduleDetails) {
        Optional<ClassSchedule> optionalSchedule = classScheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new RuntimeException("ClassSchedule not found with id " + id);
        }

        ClassSchedule existingSchedule = optionalSchedule.get();
        existingSchedule.setClassEntity(scheduleDetails.getClassEntity());
        existingSchedule.setDate(scheduleDetails.getDate());
        existingSchedule.setStartTime(scheduleDetails.getStartTime());
        existingSchedule.setEndTime(scheduleDetails.getEndTime());
        return classScheduleRepository.save(existingSchedule);
    }

    public boolean deleteSchedule(Long id) {
        if (classScheduleRepository.existsById(id)) {
            classScheduleRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("ClassSchedule not found with id " + id);
        }
    }

    private void validateSchedule(ClassSchedule schedule) {
        if (schedule.getClassEntity() == null) {
            throw new IllegalArgumentException("Class entity cannot be null");
        }
        if (schedule.getDate() == null) {
            throw new IllegalArgumentException("Schedule date cannot be null");
        }
        if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
            throw new IllegalArgumentException("Start and end times cannot be null");
        }
        if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
