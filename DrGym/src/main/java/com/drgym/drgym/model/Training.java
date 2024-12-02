// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="workouts")
public class Training {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_datetime")
    private LocalDateTime dateStart;

    @Column(name = "end_datetime")
    private LocalDateTime dateEnd;

    @Column(name = "description")
    private String description;

    @Column(name = "created_datetime")
    private LocalDateTime dateCreated;

    public Training() {
    }

    public Training(Long id, LocalDateTime dateStart, LocalDateTime dateEnd, String description, LocalDateTime create_datetime) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.dateCreated = create_datetime;
    }

    // getters

    public Long getId() {return id;}

    public LocalDateTime getDateEnd() {return dateEnd;}

    public LocalDateTime getDateStart() {return dateStart;}

    public String getDescription() {return description;}

    public LocalDateTime getDateCreated() {return dateCreated;}

    // setters

    public void setId(Long id) {this.id = id;}

    public void setDateStart(LocalDateTime dateStart) {this.dateStart = dateStart;}

    public void setDateEnd(LocalDateTime dateEnd) {this.dateEnd = dateEnd;}

    public void setDescription(String description) {this.description = description;}

    public void setDateCreated(LocalDateTime dateCreated) {this.dateCreated = dateCreated;}

}

