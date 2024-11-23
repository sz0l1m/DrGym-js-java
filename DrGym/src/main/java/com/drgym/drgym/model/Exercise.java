<<<<<<< DrGym/src/main/java/com/drgym/drgym/model/Exercise.java
//author: Michał Pędziwiatr
package com.drgym.drgym.model;

import java.util.ArrayList;


public abstract class Exercise {
    private String name;
    private ArrayList<String> musclesWorked;
    private int id;

    public Exercise(String name, ArrayList<String> musclesWorked){
        this.name = name;
        if(musclesWorked != null){
            this.musclesWorked = new ArrayList<>(musclesWorked);
        }
        else{
            this.musclesWorked = new ArrayList<>();
        }
    }

    public int getId(){
        return id;
    }

    public void getId(int newId){
        id = newId;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public ArrayList<String> getMusclesWorked(){
        return new ArrayList<>(musclesWorked);
    }

    public void setMusclesWorked(ArrayList<String> newMusclesWorked){
        musclesWorked = newMusclesWorked;
    }

    public void addToMusclesWorked(String newMuscle){
        musclesWorked.add(newMuscle);
    }

    public void removeFromMusclesWorked(String muscleToRemove){
        if(musclesWorked.contains(muscleToRemove)){
            musclesWorked.remove(muscleToRemove);
            }
    }

    @Override
    public String toString(){
        String output = "Exercise name: " + name + ", muscles worked: " + musclesWorked + ".";
        return output;
    }

}
