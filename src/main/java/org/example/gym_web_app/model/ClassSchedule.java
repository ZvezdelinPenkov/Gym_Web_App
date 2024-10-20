package org.example.gym_web_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Setter
@Getter
@Entity
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private String trainerName;

    private LocalDateTime scheduledTime;

    @OneToMany(mappedBy = "classSchedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Attendance> attendances = new HashSet<>();

    @ManyToMany(mappedBy = "classSchedules", fetch = FetchType.LAZY)
    private Set<Member> members = new HashSet<>();

    public ClassSchedule() {}

    public ClassSchedule(String className, String trainerName, LocalDateTime scheduledTime) {
        this.className = className;
        this.trainerName = trainerName;
        this.scheduledTime = scheduledTime;
    }

}
