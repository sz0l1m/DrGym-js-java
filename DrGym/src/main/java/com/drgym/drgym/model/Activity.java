//author: Michał Pędziwiatr
package com.drgym.drgym.model;


public class Activity {
    private Exercise exercise;
    private int id;

    public Activity(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getId(){
        return id;
    }

    public void getId(int newId){
        id = newId;
    }

    public Exercise getExercise(){
        return exercise;
    }

    public void setExercise(Exercise newExercise){
        exercise = newExercise;
    }

    @Override
    public String toString(){
        String output = exercise.toString();
        return output;
    }
}