package com.drgym.drgym.model;

import java.util.ArrayList;

public class ExerciseCreateRequest {
    private String name;
    private char type;
    private Long kcalBurned;
    private ArrayList<String> musclesWorked;

    public ExerciseCreateRequest() {
    }

    public ExerciseCreateRequest(String name, char type, Long kcalBurned , ArrayList<String> musclesWorked) {
        this.name = name;
        this.type = type;
        this.kcalBurned = kcalBurned;
        this.musclesWorked = musclesWorked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Long getKcalBurned() {
        return kcalBurned;
    }

    public void setKcalBurned(Long kcalBurned) {
        this.kcalBurned = kcalBurned;
    }

    public ArrayList<String> getMusclesWorked() {
        return musclesWorked;
    }

    public void setMusclesWorked(ArrayList<String> musclesWorked) {
        this.musclesWorked = musclesWorked;
    }
}