package com.drgym.drgym.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id", nullable = false)
    private Long id;

    @Column(name = "exercise_id", nullable = false)
    private Long exercise_id;

    @Column(name = "reps")
    private Long reps;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "duration")
    @JsonSerialize(using = TimestampFormatted.class)
    private Timestamp duration;

    @Column(name = "workout_id")
    private Long workoutId;

    @Transient
    private String exerciseName;

    @Transient
    private char exerciseType;

    public Activity(Long exercise_id, Long reps, Long weight, Timestamp duration, Long workoutId) {
        this.exercise_id = exercise_id;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
        this.workoutId = workoutId;
    }

    public Activity() {}

    // getters

    public Long getId() { return id; }

    public Long getExerciseId() { return exercise_id; }

    public Long getReps() { return reps; }

    public Long getWeight() { return weight; }

    public Timestamp getDuration() { return duration; }

    public Long getWorkoutId() { return workoutId; }

    public String getExerciseName() { return exerciseName; }

    // setters

    public void setId(Long id) { this.id = id; }

    public void setExerciseId(Long exercise_id) { this.exercise_id = exercise_id; }

    public void setReps(Long reps) { this.reps = reps; }

    public void setWeight(Long weight) { this.weight = weight; }

    public void setDuration(Timestamp duration) { this.duration = duration; }

    public void setDuration(String duration) {
        LocalTime localTime = LocalTime.parse(duration);
        this.duration = Timestamp.valueOf(localTime.atDate(LocalDate.now()));
    }

    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public String durationToString() {
        return duration.toString();
    }

    public char getExerciseType() { return exerciseType; }

    public void setExerciseType(char exerciseType) { this.exerciseType = exerciseType; }
}