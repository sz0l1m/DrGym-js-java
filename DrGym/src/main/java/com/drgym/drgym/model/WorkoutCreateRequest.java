package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutCreateRequest {
    private String username;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Activity> activities;

    public WorkoutCreateRequest() {
    }

    public WorkoutCreateRequest(String username, String description, LocalDateTime startDate, LocalDateTime endDate, List<Activity> activities) {
        this.username = username;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}