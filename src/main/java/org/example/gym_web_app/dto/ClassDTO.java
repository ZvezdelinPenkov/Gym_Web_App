package org.example.gym_web_app.dto;

import java.util.Set;

public class ClassDTO {

    private Long id;
    private String title;
    private int duration;
    private int maxParticipants;
    private Long instructorId;
    private Set<Long> scheduleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Set<Long> getScheduleIds() {
        return scheduleIds;
    }

    public void setScheduleIds(Set<Long> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }
}
