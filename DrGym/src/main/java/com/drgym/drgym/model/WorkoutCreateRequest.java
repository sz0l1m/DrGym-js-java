package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutCreateRequest {
    private String username;
    private String description;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime createdDatetime;
    private List<Activity> activities;

    public WorkoutCreateRequest() {
    }

    public WorkoutCreateRequest(String username, String description, LocalDateTime startDatetime, LocalDateTime endDatetime, LocalDateTime createdDatetime, List<Activity> activities) {
        this.username = username;
        this.description = description;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.createdDatetime = createdDatetime;
        this.activities = activities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
