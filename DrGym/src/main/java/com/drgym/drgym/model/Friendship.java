// author: ksiemion
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
    private String friend1_username;

    @Column(name = "friend2_username")
    private String friend2_username;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Friendship() {}

    public Friendship(Long id, String friend1_username, String friend2_username) {
        this.id = id;
        this.friend1_username = friend1_username;
        this.friend2_username = friend2_username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriend1_username() {
        return friend1_username;
    }

    public void setFriend1_username(String friend1_username) {
        this.friend1_username = friend1_username;
    }

    public String getFriend2_username() {
        return friend2_username;
    }

    public void setFriend2_username(String friend2_username) {
        this.friend2_username = friend2_username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
