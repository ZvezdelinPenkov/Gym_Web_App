package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.exception.InvalidRequestException;
import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.repository.AttendanceRepository;
import org.example.gym_web_app.util.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public AttendanceDTO getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
        return AttendanceMapper.toDTO(attendance);
    }

    public AttendanceDTO addAttendance(AttendanceDTO attendanceDTO) {
        validateAttendanceInput(attendanceDTO);

        Attendance attendance = AttendanceMapper.toEntity(attendanceDTO);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return AttendanceMapper.toDTO(savedAttendance);
    }

    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        validateAttendanceInput(attendanceDTO);

        Attendance existingAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));

        existingAttendance.setAttendanceTime(attendanceDTO.getAttendanceTime());
        existingAttendance.setAttended(attendanceDTO.isAttended());
        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);

        return AttendanceMapper.toDTO(updatedAttendance);
    }

    public boolean deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attendance not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
        return false;
    }

    private void validateAttendanceInput(AttendanceDTO attendanceDTO) {
        if (attendanceDTO.getAttendanceTime() == null) {
            throw new InvalidRequestException("Attendance time cannot be null");
        }
        if (attendanceDTO.getMemberId() == null || attendanceDTO.getClassScheduleId() == null) {
            throw new InvalidRequestException("Member ID and Class Schedule ID cannot be null");
        }
    }
}
