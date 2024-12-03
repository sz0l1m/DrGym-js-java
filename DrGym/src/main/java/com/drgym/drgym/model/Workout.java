// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="workouts")
public class Workout {
    @Id
    @Column(name = "workout_id", nullable = false)
    private Long id;

    @Column(name = "start_datetime")
    private LocalDateTime dateStart;

    @Column(name = "username")
    private String username;

    @Column(name = "end_datetime")
    private LocalDateTime dateEnd;

    @Column(name = "description")
    private String description;

    @Column(name = "created_datetime")
    private LocalDateTime dateCreated;

    @ElementCollection
    @CollectionTable(
            name = "workout_activities",
            joinColumns = @JoinColumn(name = "workout_id")
    )
    @Column(name = "activity_id")
    private List<Long> activityIds;
    public Workout() {
    }

    public Workout(Long id, LocalDateTime dateStart, String username, LocalDateTime dateEnd, String description, LocalDateTime create_datetime, List<Long> activityIds) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.id = id;
        this.username = username;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.dateCreated = create_datetime;
        this.activityIds = activityIds;
    }

    // getters

    public Long getId() {return id;}

    public LocalDateTime getDateEnd() {return dateEnd;}

    public LocalDateTime getDateStart() {return dateStart;}

    public String getDescription() {return description;}

    public LocalDateTime getDateCreated() {return dateCreated;}

    public List<Long> getActivityIds() {return activityIds;}

    public String getUsername() {return username;}

    // setters

    public void setId(Long id) {this.id = id;}

    public void setDateStart(LocalDateTime dateStart) {this.dateStart = dateStart;}

    public void setDateEnd(LocalDateTime dateEnd) {this.dateEnd = dateEnd;}

    public void setDescription(String description) {this.description = description;}

    public void setDateCreated(LocalDateTime dateCreated) {this.dateCreated = dateCreated;}

    public void setActivityIds(List<Long> activityIds) {this.activityIds = activityIds;}

    public void setUsername(String username) {this.username = username;}

}

