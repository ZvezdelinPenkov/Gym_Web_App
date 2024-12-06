package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.model.Attendance;

public class AttendanceMapper {

    private AttendanceMapper() {

    }

    public static AttendanceDTO toDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setMemberId(attendance.getMember() != null ? attendance.getMember().getId() : null);
        dto.setClassScheduleId(attendance.getClassSchedule() != null ? attendance.getClassSchedule().getId() : null);
        dto.setAttendanceTime(attendance.getAttendanceTime());
        dto.setAttended(attendance.isAttended());
        return dto;
    }

    public static Attendance toEntity(AttendanceDTO dto) {
        Attendance attendance = new Attendance();
        attendance.setId(dto.getId());
        attendance.setAttendanceTime(dto.getAttendanceTime());
        attendance.setAttended(dto.isAttended());
        return attendance;
    }
}
