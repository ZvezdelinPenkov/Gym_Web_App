package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.ClassRepository;
import org.example.gym_web_app.repository.UsersRepository;

import org.example.gym_web_app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClassMapper {

    private ClassMapper() {

    }

    public static ClassDTO toDTO(Class classEntity) {
        ClassDTO dto = new ClassDTO();
        dto.setId(classEntity.getId());
        dto.setTitle(classEntity.getTitle());
        dto.setDuration(classEntity.getDuration());
        dto.setMaxParticipants(classEntity.getMaxParticipants());
        dto.setInstructorId(classEntity.getInstructor().getId());
        dto.setScheduleIds(
                classEntity.getSchedules().stream()
                        .map(schedule -> schedule.getId())
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public static Class toEntity(ClassDTO dto, UsersRepository usersRepository) {

        Users user = usersRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"));

        Class classEntity = new Class();
        classEntity.setId(dto.getId());
        classEntity.setTitle(dto.getTitle());
        classEntity.setDuration(dto.getDuration());
        classEntity.setMaxParticipants(dto.getMaxParticipants());
//        classEntity.setInstructor(dto.getInstructorId());
        classEntity.setInstructor(user);
        return classEntity;
    }
}
