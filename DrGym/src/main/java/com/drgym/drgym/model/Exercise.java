//author: Michał Pędziwiatr
package com.drgym.drgym.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "type")
    private char type;

    @Column(name = "kcal_burned")
    private Long kcalBurned;

    @Column(name = "name")
    private String name;

    @Column(name = "video_id")
    private String videoId;

    @ElementCollection
    @CollectionTable(name = "exercises_muscles", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "muscle_id")
    private List<String> musclesWorked;

    public Exercise() {
    }

    public Exercise(char type, Long kcalBurned, String name, List<String> musclesWorked) {
        this.type = type;
        this.kcalBurned = kcalBurned;
        this.name = name;
        this.musclesWorked = musclesWorked;
    }

    public Exercise(char type, Long kcalBurned, String name, List<String> musclesWorked, String videoId) {
        this.type = type;
        this.kcalBurned = kcalBurned;
        this.name = name;
        this.musclesWorked = musclesWorked;
        this.videoId = videoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMusclesWorked() {
        return musclesWorked;
    }

    public void setMusclesWorked(List<String> musclesWorked) {
        this.musclesWorked = musclesWorked;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}