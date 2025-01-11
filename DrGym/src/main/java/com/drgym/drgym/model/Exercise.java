//author: Michał Pędziwiatr
package com.drgym.drgym.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "exercise_id")
    private Long id;

    @Column (name = "type")
    private char type;

    @Column (name = "kcal_burned")
    private Long kcalBurned;

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
        this.kcalBurned = kcal_burned;
        this.name = name;
        if (musclesWorked != null) {
            this.musclesWorked = new ArrayList<>(musclesWorked);
        } else {
            this.musclesWorked = new ArrayList<>();
        }
    }

    public Exercise(String name, char type, Long kcalBurned, List<String> musclesWorked) {
        this.name = name;
        this.type = type;
        this.kcalBurned = kcalBurned;
        this.musclesWorked = musclesWorked;
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
        return kcalBurned;
    }

    public void setKcal_burned(Long newKcal_burned) {
        kcalBurned = newKcal_burned;
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