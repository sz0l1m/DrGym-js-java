package com.drgym.drgym.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workout_activities")
public class WorkoutActivity {

    @Id
    @Column(name = "workout_activity_id", nullable = false)
    private Long id;

    @Column(name = "workout_id")
    private Long workoutId;

    @Column(name = "activity_id")
    private Long activityId;

    public WorkoutActivity() {
    }

    public WorkoutActivity(Long id, Long workoutId, Long activityId) {
        this.id = id;
        this.workoutId = workoutId;
        this.activityId = activityId;
    }

    // Gettery i Settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
