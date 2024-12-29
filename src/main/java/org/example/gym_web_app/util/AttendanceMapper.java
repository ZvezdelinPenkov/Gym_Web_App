package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.AttendanceDTO;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.example.gym_web_app.repository.MemberRepository;

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

    public static Attendance toEntity(AttendanceDTO dto, MemberRepository memberRepository, ClassScheduleRepository classScheduleRepository) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id"));

        ClassSchedule classSchedule = classScheduleRepository.findById(dto.getClassScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("ClassSchedule not found with id"));


        Attendance attendance = new Attendance();
        attendance.setId(dto.getId());
        attendance.setAttendanceTime(dto.getAttendanceTime());
        attendance.setAttended(dto.isAttended());
        attendance.setMember(member);
        attendance.setClassSchedule(classSchedule);
        return attendance;
    }
}
