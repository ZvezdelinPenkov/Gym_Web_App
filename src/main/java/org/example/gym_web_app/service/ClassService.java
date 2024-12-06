package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.ClassDTO;
import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.repository.ClassRepository;
import org.example.gym_web_app.util.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<ClassDTO> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(ClassMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClassDTO> getClassById(Long id) {
        return classRepository.findById(id).map(ClassMapper::toDTO);
    }

    public ClassDTO addClass(ClassDTO classDTO) {
        Class classEntity = ClassMapper.toEntity(classDTO);
        Class savedClass = classRepository.save(classEntity);
        return ClassMapper.toDTO(savedClass);
    }

    public ClassDTO updateClass(Long id, ClassDTO classDTO) {
        Optional<Class> optionalClass = classRepository.findById(id);
        if (optionalClass.isEmpty()) {
            throw new RuntimeException("Class not found with id " + id);
        }

        Class classEntity = optionalClass.get();
        classEntity.setTitle(classDTO.getTitle());
        classEntity.setDuration(classDTO.getDuration());
        classEntity.setMaxParticipants(classDTO.getMaxParticipants());
        Class updatedClass = classRepository.save(classEntity);
        return ClassMapper.toDTO(updatedClass);
    }

    public boolean deleteClass(Long id) {
        if (classRepository.existsById(id)) {
            classRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Class not found with id " + id);
        }
    }
}
