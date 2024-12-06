package org.example.gym_web_app.service;

import org.example.gym_web_app.model.Class;
import org.example.gym_web_app.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;


    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<Class> getClassById(Long id) {
        return classRepository.findById(id);
    }


    public Class addClass(Class classEntity) {
        validateClass(classEntity);
        return classRepository.save(classEntity);
    }


    public Class updateClass(Long id, Class classDetails) {
        Optional<Class> optionalClass = classRepository.findById(id);
        if (optionalClass.isEmpty()) {
            throw new RuntimeException("Class not found with id " + id);
        }

        Class existingClass = optionalClass.get();
        existingClass.setTitle(classDetails.getTitle());
        existingClass.setDuration(classDetails.getDuration());
        existingClass.setMaxParticipants(classDetails.getMaxParticipants());
        existingClass.setInstructor(classDetails.getInstructor());
        return classRepository.save(existingClass);
    }

    public boolean deleteClass(Long id) {
        if (classRepository.existsById(id)) {
            classRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Class not found with id " + id);
        }
    }

    private void validateClass(Class classEntity) {
        if (classEntity.getTitle() == null || classEntity.getTitle().isBlank()) {
            throw new IllegalArgumentException("Class title cannot be null or empty");
        }
        if (classEntity.getDuration() <= 0) {
            throw new IllegalArgumentException("Class duration must be greater than 0");
        }
        if (classEntity.getMaxParticipants() <= 0) {
            throw new IllegalArgumentException("Max participants must be greater than 0");
        }
    }
}
