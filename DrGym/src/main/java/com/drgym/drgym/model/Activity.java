package com.drgym.drgym.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

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
    private Timestamp duration;

    public Activity(Long exercise_id, Long reps, Long weight, Timestamp duration) {
        this.exercise_id = exercise_id;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
    }

    public Activity() {}

    // getters

    public Long getId() {return id;}

    public Long getExerciseId() {return exercise_id;}

    public Long getReps() {return reps;}

    public Long getWeight() {return weight;}

    public Timestamp getDuration() {return duration;}

    // setters

    public void setId(Long id_workout) {this.id = id_workout;}

    public void setExerciseId(Long exercise_id) {this.exercise_id = exercise_id;}

    public void setReps(Long reps) {this.reps = reps;}

    public void setWeight(Long weight) {this.weight = weight;}

    public void setDuration(Timestamp duration) {this.duration = duration;}

    public String durationToString() {
        return duration.toString();
    }
}