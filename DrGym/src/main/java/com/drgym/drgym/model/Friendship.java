// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class Friendship {
    private Long id;
    private String friend1_username;
    private String friend2_username;
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
