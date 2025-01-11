// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class FriendshipInvitation {
    private Long id;
    private String whoSendUsername;
    private String whoReceiveUsername;
    private LocalDateTime sendTime;

    public FriendshipInvitation() {}

    public FriendshipInvitation(Long id, String whoSendUsername, String whoReceiveUsername, LocalDateTime sendTime) {
        this.id = id;
        this.whoSendUsername = whoSendUsername;
        this.whoReceiveUsername = whoReceiveUsername;
        this.sendTime = sendTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhoSendUsername() {
        return whoSendUsername;
    }

    public void setWhoSendUsername(String whoSendUsername) {
        this.whoSendUsername = whoSendUsername;
    }

    public String getWhoReceiveUsername() {
        return whoReceiveUsername;
    }

    public void setWhoReceiveUsername(String whoReceiveUsername) {
        this.whoReceiveUsername = whoReceiveUsername;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
