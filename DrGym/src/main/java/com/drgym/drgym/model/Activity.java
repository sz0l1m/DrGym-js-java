//author: Michał Pędziwiatr
package com.drgym.drgym.model;


import jakarta.persistence.Id;

public class Activity {
    private Integer id_workout;
    private Integer exercise_id;

    public Activity(Integer id_workout, Integer exercise_id) {

        this.id_workout = id_workout;
        this.exercise_id = exercise_id;
    }

    public Integer getId_workout() {return id_workout;}

    public Integer getExercise_id() {return exercise_id;}

    public void setId_workout(Integer id_workout) {this.id_workout = id_workout;}

    public void setExercise_id(Integer exercise_id) {this.exercise_id = exercise_id;}
}