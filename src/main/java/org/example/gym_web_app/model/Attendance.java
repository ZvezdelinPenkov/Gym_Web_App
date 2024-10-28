package org.example.gym_web_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "class_schedule_id", nullable = false)
    private ClassSchedule classSchedule;

    private LocalDateTime attendanceTime;

    public Attendance() {}

    public Attendance(Member member, ClassSchedule classSchedule, LocalDateTime attendanceTime) {
        this.member = member;
        this.classSchedule = classSchedule;
        this.attendanceTime = attendanceTime;
    }

}
