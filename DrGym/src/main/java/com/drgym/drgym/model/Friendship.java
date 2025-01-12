package com.drgym.drgym.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "friend1_username")
    private String friend1Username;

    @Column(name = "friend2_username")
    private String friend2Username;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Friendship() {}

    public Friendship(Long id, String friend1Username, String friend2Username) {
        this.id = id;
        this.friend1Username = friend1Username;
        this.friend2Username = friend2Username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriend1Username() {
        return friend1Username;
    }

    public void setFriend1Username(String friend1Username) {
        this.friend1Username = friend1Username;
    }

    public String getFriend2Username() {
        return friend2Username;
    }

    public void setFriend2Username(String friend2Username) {
        this.friend2Username = friend2Username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}