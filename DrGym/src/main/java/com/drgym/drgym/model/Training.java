// author: ksiemion
package com.drgym.drgym.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Training {
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Duration totalTime;
    private List<Exercise> exercises = new ArrayList<>();

    public Training() {}

    public Training(LocalDateTime dateStart, LocalDateTime dateEnd, List<Exercise> exercises) {
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }

    public void addExercise(Exercise exercise) {
        if(!this.getExercises().contains(exercise)) {
            this.getExercises().add(exercise);
        }
    }

    public void removeExercise(Exercise exercise) {
        this.getExercises().remove(exercise);
    }

    public void clearExercises() {
        this.getExercises().clear();
    }
}
