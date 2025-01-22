package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutUpdateRequest {
    private Long id;
    private String username;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> activitiesToRemove;
    private List<Activity> activitiesToAdd;
    private int schedule;

    public WorkoutUpdateRequest() {
    }

    public WorkoutUpdateRequest(Long id, String username, String description, LocalDateTime startDate, LocalDateTime endDate, List<Long> activitiesToRemove, List<Activity> activitiesToAdd, int schedule) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activitiesToRemove = activitiesToRemove;
        this.activitiesToAdd = activitiesToAdd;
        this.schedule = schedule;
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

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}