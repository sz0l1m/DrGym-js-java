//author: Michał Pędziwiatr
package com.drgym.drgym.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @Column (name = "exercise_id")
    private Long id;

    @Column (name = "type")
    private char type;

    @Column (name = "kcal_burned")
    private Long kcal_burned;

    @Column (name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "exercises_muscles", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "muscle_id")
    private List<String> musclesWorked;

    public Exercise() {
    }

    public Exercise(Long id, char type, Long kcal_burned, String name, ArrayList<String> musclesWorked) {
        this.id = id;
        this.type = type;
        this.kcal_burned = kcal_burned;
        this.name = name;
        if (musclesWorked != null) {
            this.musclesWorked = new ArrayList<>(musclesWorked);
        } else {
            this.musclesWorked = new ArrayList<>();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public char getType() {
        return type;
    }

    public void setType(char newType) {
        type = newType;
    }

    public Long getKcal_burned() {
        return kcal_burned;
    }

    public void setKcal_burned(Long newKcal_burned) {
        kcal_burned = newKcal_burned;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public ArrayList<String> getMusclesWorked() {
        return new ArrayList<>(musclesWorked);
    }

    public void setMusclesWorked(ArrayList<String> newMusclesWorked) {
        musclesWorked = newMusclesWorked;
    }

    public void addToMusclesWorked(String newMuscle) {
        musclesWorked.add(newMuscle);
    }

    public void removeFromMusclesWorked(String muscleToRemove) {
        if (musclesWorked.contains(muscleToRemove)) {
            musclesWorked.remove(muscleToRemove);
        }
    }

    public void clearMusclesWorked() {
        musclesWorked.clear();
    }
}