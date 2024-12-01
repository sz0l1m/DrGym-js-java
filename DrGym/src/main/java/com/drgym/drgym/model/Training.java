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

    public Training() {}

    public Training(LocalDateTime dateStart, LocalDateTime dateEnd, List<Long> exercises) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Training(LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (dateStart.isAfter(dateEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public String getDescription() {return description;}

}



