package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        List<AttendanceDTO> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        Optional<AttendanceDTO> attendance = attendanceService.getAttendanceById(id);
        return attendance.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> addAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceDTO createdAttendance = attendanceService.addAttendance(attendanceDTO);
            return ResponseEntity.status(201).body(createdAttendance);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceDTO updatedAttendance = attendanceService.updateAttendance(id, attendanceDTO);
            return ResponseEntity.ok(updatedAttendance);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        try {
            if (attendanceService.deleteAttendance(id)) {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
