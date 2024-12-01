//author: Michał Pędziwiatr
package com.drgym.drgym.model;

import java.time.LocalTime;
import java.util.ArrayList;


public class CardioExercise extends Exercise {
    private LocalTime duration;

    public CardioExercise(String name, ArrayList<String> musclesWorked, LocalTime duration) {
        super(name, musclesWorked);
        this.duration = duration;
    }

    public CardioExercise(String name, ArrayList<String> musclesWorked) {
        this(name, musclesWorked, LocalTime.of(0, 0));
    }

    public CardioExercise() {}

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime newDuration) {
        this.duration = newDuration;
    }

    @Override
    public String toString(){
        String output = super.toString() + ",\nDuration: " + duration;
        return output;
    }

}