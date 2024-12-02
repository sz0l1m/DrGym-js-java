package com.drgym.drgym.model;

import jakarta.persistence.Id;

import java.sql.Timestamp;

public class Newactivity {
    private Integer id_workout;
    private Integer exercise_id;
    private Integer sets;
    private Integer weight;
    private Timestamp duration;

    public Newactivity(Integer id_workout, Integer exercise_id, Integer sets, Integer weight, Timestamp duration) {
        this.id_workout = id_workout;
        this.exercise_id = exercise_id;
        this.sets = sets;
        this.weight = weight;
        this.duration = duration;
    }

    // getters

    public Integer getId_workout() {return id_workout;}

    public Integer getExercise_id() {return exercise_id;}

    public Integer getSets() {return sets;}

    public Integer getWeight() {return weight;}

    public Timestamp getDuration() {return duration;}

    // setters

    public void setId_workout(Integer id_workout) {this.id_workout = id_workout;}

    public void setExercise_id(Integer exercise_id) {this.exercise_id = exercise_id;}

    public void setSets(Integer sets) {this.sets = sets;}

    public void setWeight(Integer weight) {this.weight = weight;}

    public void setDuration(Timestamp duration) {this.duration = duration;}
}