// author: ksiemion
package com.drgym.drgym.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Training {
    private Long id;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Duration totalTime;
    private List<Long> exercises = new ArrayList<>();

    public Training() {}

    public Training(LocalDateTime dateStart, LocalDateTime dateEnd, List<Long> exercises) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.totalTime = Duration.between(dateStart, dateEnd);
        this.exercises = exercises;
    }

    public Training(LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.totalTime = Duration.between(dateStart, dateEnd);
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public Duration getTotalTime() {
        return totalTime;
    }

    public List<Long> getExercises() {
        return exercises;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setExercises(List<Long> exercises) {
        this.exercises = exercises;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }

    public void addExercise(Long exerciseId) {
        if(!this.getExercises().contains(exerciseId)) {
            this.getExercises().add(exerciseId);
        }
    }

    public void removeExercise(Long exerciseId) {
        this.getExercises().remove(exerciseId);
    }

    public void clearExercises() {
        this.getExercises().clear();
    }
}
