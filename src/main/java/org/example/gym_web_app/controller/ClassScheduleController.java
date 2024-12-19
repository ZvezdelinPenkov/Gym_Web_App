package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class-schedules")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService classScheduleService;

    @GetMapping
    public ResponseEntity<List<ClassScheduleDTO>> getAllSchedules() {
        List<ClassScheduleDTO> schedules = classScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleDTO> getScheduleById(@PathVariable Long id) {
        Optional<ClassScheduleDTO> schedule = Optional.ofNullable(classScheduleService.getScheduleById(id));
        return schedule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassScheduleDTO> addSchedule(@RequestBody ClassScheduleDTO scheduleDTO) {
        try {
            ClassScheduleDTO createdSchedule = classScheduleService.addSchedule(scheduleDTO);
            return ResponseEntity.status(201).body(createdSchedule);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassScheduleDTO> updateSchedule(@PathVariable Long id, @RequestBody ClassScheduleDTO scheduleDTO) {
        try {
            ClassScheduleDTO updatedSchedule = classScheduleService.updateSchedule(id, scheduleDTO);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        try {
            if (classScheduleService.deleteSchedule(id)) {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
