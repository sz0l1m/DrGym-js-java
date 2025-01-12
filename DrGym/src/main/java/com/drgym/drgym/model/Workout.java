package com.drgym.drgym.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name="workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id", nullable = false)
    private Long id;

    @Column(name = "start_datetime")
    private LocalDateTime startDate;

    @Column(name = "username")
    private String username;

    @Column(name = "end_datetime")
    private LocalDateTime endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "created_datetime")
    private LocalDateTime dateCreated;

    public Workout() {
    }

    public Workout(LocalDateTime startDate, String username, LocalDateTime endDate, String description, LocalDateTime dateCreated) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    // getters and setters
    public Long getId() { return id; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}