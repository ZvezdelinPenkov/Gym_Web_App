package org.example.gym_web_app.repository;

import org.example.gym_web_app.dto.SignupDTO;
import org.example.gym_web_app.model.Attendance;
import org.example.gym_web_app.model.Signup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignupRepository extends JpaRepository<Signup, Long> {

    long countByClassScheduleId(Long classScheduleId);

    @Query("SELECT c.maxParticipants FROM Class c JOIN ClassSchedule cs ON c.id = cs.classId WHERE cs.id = :classScheduleId")
    long getMaxParticipantsByClassId(Long classScheduleId);
}
