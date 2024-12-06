package org.example.gym_web_app.service;

import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }


    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }


    public Attendance addAttendance(Attendance attendance) {
        validateAttendance(attendance);
        if (attendance.getAttendanceTime() == null) {
            attendance.setAttendanceTime(LocalDateTime.now());
        }
        return attendanceRepository.save(attendance);
    }


    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if (optionalAttendance.isEmpty()) {
            throw new RuntimeException("Attendance not found with id " + id);
        }

        Attendance existingAttendance = optionalAttendance.get();
        existingAttendance.setMember(attendanceDetails.getMember());
        existingAttendance.setClassSchedule(attendanceDetails.getClassSchedule());
        existingAttendance.setAttendanceTime(attendanceDetails.getAttendanceTime());
        existingAttendance.setAttended(attendanceDetails.isAttended());
        return attendanceRepository.save(existingAttendance);
    }

    public boolean deleteAttendance(Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Attendance not found with id " + id);
        }
    }

    private void validateAttendance(Attendance attendance) {
        if (attendance.getMember() == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (attendance.getClassSchedule() == null) {
            throw new IllegalArgumentException("ClassSchedule cannot be null");
        }
    }
}
