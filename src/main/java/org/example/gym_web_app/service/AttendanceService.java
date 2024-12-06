package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.repository.AttendanceRepository;
import org.example.gym_web_app.util.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AttendanceDTO> getAttendanceById(Long id) {
        return attendanceRepository.findById(id).map(AttendanceMapper::toDTO);
    }

    public AttendanceDTO addAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = AttendanceMapper.toEntity(attendanceDTO);
        // Fetch and set associated Member and ClassSchedule in the service layer
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return AttendanceMapper.toDTO(savedAttendance);
    }

    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if (optionalAttendance.isEmpty()) {
            throw new RuntimeException("Attendance not found with id " + id);
        }

        Attendance attendance = optionalAttendance.get();
        // Update fields
        attendance.setAttendanceTime(attendanceDTO.getAttendanceTime());
        attendance.setAttended(attendanceDTO.isAttended());
        // Fetch and set associated Member and ClassSchedule if needed
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return AttendanceMapper.toDTO(updatedAttendance);
    }

    public boolean deleteAttendance(Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Attendance not found with id " + id);
        }
    }
}
