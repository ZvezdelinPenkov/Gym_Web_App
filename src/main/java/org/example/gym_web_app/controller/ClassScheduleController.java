package org.example.gym_web_app.controller;

import org.example.gym_web_app.model.ClassSchedule;
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
    public ResponseEntity<List<ClassSchedule>> getAllSchedules() {
        List<ClassSchedule> schedules = classScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedule> getScheduleById(@PathVariable Long id) {
        Optional<ClassSchedule> schedule = classScheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassSchedule> addSchedule(@RequestBody ClassSchedule schedule) {
        try {
            ClassSchedule createdSchedule = classScheduleService.addSchedule(schedule);
            return ResponseEntity.status(201).body(createdSchedule);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSchedule> updateSchedule(@PathVariable Long id, @RequestBody ClassSchedule scheduleDetails) {
        try {
            ClassSchedule updatedSchedule = classScheduleService.updateSchedule(id, scheduleDetails);
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
