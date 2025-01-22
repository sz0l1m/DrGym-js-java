package com.drgym.drgym.model;

public class PostCreateRequestWorkout {
    private String username;
    private String title;
    private String content;
    private WorkoutCreateRequest workout;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WorkoutCreateRequest getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutCreateRequest workout) {
        this.workout = workout;
    }
}