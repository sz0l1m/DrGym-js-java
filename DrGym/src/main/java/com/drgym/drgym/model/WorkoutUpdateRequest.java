package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutUpdateRequest {
    private Long id;
    private String username;
    private String description;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private List<Long> activitiesToRemove;
    private List<Activity> activitiesToAdd;

    public WorkoutUpdateRequest() {
    }

    public WorkoutUpdateRequest(Long id, String username, String description, LocalDateTime startDatetime, LocalDateTime endDatetime, List<Long> activitiesToRemove, List<Activity> activitiesToAdd) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.activitiesToRemove = activitiesToRemove;
        this.activitiesToAdd = activitiesToAdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getActivitiesToRemove() {
        return activitiesToRemove;
    }

    public void setActivitiesToRemove(List<Long> activitiesToRemove) {
        this.activitiesToRemove = activitiesToRemove;
    }

    public List<Activity> getActivitiesToAdd() {
        return activitiesToAdd;
    }

    public void setActivitiesToAdd(List<Activity> activitiesToAdd) {
        this.activitiesToAdd = activitiesToAdd;
    }
}