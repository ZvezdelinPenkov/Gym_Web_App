package org.example.gym_web_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

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

    @ManyToMany(mappedBy = "classSchedules", fetch = FetchType.LAZY)
    private Set<Member> members = new HashSet<>();

    public ClassSchedule() {}

    public ClassSchedule(String className, String trainerName, LocalDateTime scheduledTime) {
        this.className = className;
        this.trainerName = trainerName;
        this.scheduledTime = scheduledTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
}
