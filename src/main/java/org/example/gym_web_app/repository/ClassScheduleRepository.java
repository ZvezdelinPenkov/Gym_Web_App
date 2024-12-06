package org.example.gym_web_app.repository;

import org.example.gym_web_app.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
}
