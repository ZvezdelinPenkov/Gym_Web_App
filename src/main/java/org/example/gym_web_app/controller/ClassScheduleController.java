package org.example.gym_web_app.controller;

import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService classScheduleService;

    // Get all classes
    @GetMapping
    public List<ClassSchedule> getAllClasses() {
        return classScheduleService.getAllClasses();
    }

    // Get class by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedule> getClassById(@PathVariable Long id) {
        ClassSchedule classSchedule = classScheduleService.getClassById(id);
        if (classSchedule != null) {
            return ResponseEntity.ok(classSchedule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new class
    @PostMapping
    public ClassSchedule createClass(@RequestBody ClassSchedule classSchedule) {
        return classScheduleService.createClass(classSchedule);
    }

    // Update an existing class
    @PutMapping("/{id}")
    public ResponseEntity<ClassSchedule> updateClass(@PathVariable Long id, @RequestBody ClassSchedule classDetails) {
        ClassSchedule updatedClass = classScheduleService.updateClass(id, classDetails);
        if (updatedClass != null) {
            return ResponseEntity.ok(updatedClass);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a class
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        boolean isDeleted = classScheduleService.deleteClass(id);
        if (isDeleted) {
            return ResponseEntity.ok("Class deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
