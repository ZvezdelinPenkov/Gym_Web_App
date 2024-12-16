package org.example.gym_web_app.repository;

import org.example.gym_web_app.dto.SignupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignupRepository extends JpaRepository<SignupDTO, Long> {

    long countByClassScheduleId(Long classScheduleId);

    @Query("SELECT c.maxParticipants FROM Class c JOIN ClassSchedule cs ON c.id = cs.classId WHERE cs.id = :classScheduleId")
    long getMaxParticipantsByClassId(Long classScheduleId);
}
