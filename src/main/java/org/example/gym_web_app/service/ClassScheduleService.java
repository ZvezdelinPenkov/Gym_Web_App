package org.example.gym_web_app.service;

import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.repository.ClassScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    public List<ClassSchedule> getAllClasses() {
        return classScheduleRepository.findAll();
    }

    public ClassSchedule getClassById(Long id) {
        Optional<ClassSchedule> classSchedule = classScheduleRepository.findById(id);
        return classSchedule.orElse(null);
    }

    public ClassSchedule createClass(ClassSchedule classSchedule) {
        return classScheduleRepository.save(classSchedule);
    }

    public ClassSchedule updateClass(Long id, ClassSchedule classDetails) {
        Optional<ClassSchedule> classSchedule = classScheduleRepository.findById(id);
        if (classSchedule.isPresent()) {
            ClassSchedule existingClass = classSchedule.get();
            existingClass.setClassName(classDetails.getClassName());
            existingClass.setTrainerName(classDetails.getTrainerName());
            existingClass.setScheduledTime(classDetails.getScheduledTime());
            // Update more fields if added (e.g., capacity)
            return classScheduleRepository.save(existingClass);
        } else {
            return null;
        }
    }

    public boolean deleteClass(Long id) {
        if (classScheduleRepository.existsById(id)) {
            classScheduleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
