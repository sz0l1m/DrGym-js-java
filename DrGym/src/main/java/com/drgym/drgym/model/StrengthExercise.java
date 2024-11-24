//author: Michał Pędziwiatr
package com.drgym.drgym.model;

import java.util.ArrayList;


public class StrengthExercise extends Exercise {
    private Integer repetitions;
    private Integer weight;
    private String weightUnit;

    public StrengthExercise(String name, ArrayList<String> musclesWorked, Integer repetitions, Integer weight, String weightUnit){
        super(name, musclesWorked);
        this.repetitions = repetitions;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    public StrengthExercise(String name, ArrayList<String> musclesWorked, Integer repetitions, Integer weight){
        this(name, musclesWorked, repetitions, weight, "kg");
    }

    public Integer getRepetitions(){
        return repetitions;
    }

    public void setRepetitions(Integer newRepetitions) {
        this.repetitions = newRepetitions;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeightUnit(){
        return weightUnit;
    }

    public void setWeightUnit(String newWeightUnit){
        weightUnit = newWeightUnit;
    }

    @Override
    public String toString(){
        String output = super.toString() + ",\n Repetitions: " + repetitions + ", Weight: " + weight + weightUnit;
        return output;
    }


}