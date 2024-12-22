package org.example.gym_web_app.service;

import jakarta.transaction.Transactional;
import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.exception.InvalidRequestException;
import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.repository.ClassRepository;
import org.example.gym_web_app.util.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(ClassMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClassDTO getClassById(Long id) {
        Class classEntity = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
        return ClassMapper.toDTO(classEntity);
    }

    public ClassDTO addClass(ClassDTO classDTO) {
        if (classDTO.getTitle() == null || classDTO.getTitle().isEmpty()) {
            throw new InvalidRequestException("Class title cannot be null or empty");
        }
        if (classDTO.getDuration() <= 0) {
            throw new InvalidRequestException("Class duration must be greater than 0 minutes");
        }
        if (classDTO.getMaxParticipants() <= 0) {
            throw new InvalidRequestException("Max participants must be greater than 0");
        }

        Class classEntity = ClassMapper.toEntity(classDTO);
        Class savedClass = classRepository.save(classEntity);
        return ClassMapper.toDTO(savedClass);
    }

    public ClassDTO updateClass(Long id, ClassDTO classDTO) {
        Class existingClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        if (classDTO.getTitle() == null || classDTO.getTitle().isEmpty()) {
            throw new InvalidRequestException("Class title cannot be null or empty");
        }
        if (classDTO.getDuration() <= 0) {
            throw new InvalidRequestException("Class duration must be greater than 0 minutes");
        }
        if (classDTO.getMaxParticipants() <= 0) {
            throw new InvalidRequestException("Max participants must be greater than 0");
        }

        existingClass.setTitle(classDTO.getTitle());
        existingClass.setDuration(classDTO.getDuration());
        existingClass.setMaxParticipants(classDTO.getMaxParticipants());
        Class updatedClass = classRepository.save(existingClass);

        return ClassMapper.toDTO(updatedClass);
    }

    public boolean deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new ResourceNotFoundException("Class not found with id: " + id);
        }
        classRepository.deleteById(id);
        return true;
    }
}
