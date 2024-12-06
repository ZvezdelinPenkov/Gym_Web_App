package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.model.Class;

import java.util.stream.Collectors;

public class ClassMapper {

    private ClassMapper() {

    }

    public static ClassDTO toDTO(Class classEntity) {
        ClassDTO dto = new ClassDTO();
        dto.setId(classEntity.getId());
        dto.setTitle(classEntity.getTitle());
        dto.setDuration(classEntity.getDuration());
        dto.setMaxParticipants(classEntity.getMaxParticipants());
        dto.setInstructorId(classEntity.getInstructor() != null ? classEntity.getInstructor().getId() : null);
        dto.setScheduleIds(
                classEntity.getSchedules().stream()
                        .map(schedule -> schedule.getId())
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public static Class toEntity(ClassDTO dto) {
        Class classEntity = new Class();
        classEntity.setId(dto.getId());
        classEntity.setTitle(dto.getTitle());
        classEntity.setDuration(dto.getDuration());
        classEntity.setMaxParticipants(dto.getMaxParticipants());
        return classEntity;
    }
}
