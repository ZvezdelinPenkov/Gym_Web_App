package org.example.gym_web_app.dto;

import java.time.LocalDateTime;

public class AttendanceDTO {

    private Long id;
    private Long memberId;
    private Long classScheduleId;
    private LocalDateTime attendanceTime;
    private boolean attended;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getClassScheduleId() {
        return classScheduleId;
    }

    public void setClassScheduleId(Long classScheduleId) {
        this.classScheduleId = classScheduleId;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
