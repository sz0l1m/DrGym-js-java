// author: ksiemion
package com.drgym.drgym.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="friendship_invitations")
public class FriendshipInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "who_send_username")
    private String whoSendUsername;

    @Column(name = "who_receive_username")
    private String whoReceiveUsername;

    @Column(name = "send_time")
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
