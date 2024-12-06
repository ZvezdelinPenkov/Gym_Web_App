package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.ClassScheduleDTO;
import org.example.gym_web_app.model.ClassSchedule;

public class ClassScheduleMapper {

    private ClassScheduleMapper() {

    }

    public static ClassScheduleDTO toDTO(ClassSchedule schedule) {
        ClassScheduleDTO dto = new ClassScheduleDTO();
        dto.setId(schedule.getId());
        dto.setClassId(schedule.getClassEntity() != null ? schedule.getClassEntity().getId() : null);
        dto.setDate(schedule.getDate());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        return dto;
    }

    public static ClassSchedule toEntity(ClassScheduleDTO dto) {
        ClassSchedule schedule = new ClassSchedule();
        schedule.setId(dto.getId());
        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        return schedule;
    }
}
