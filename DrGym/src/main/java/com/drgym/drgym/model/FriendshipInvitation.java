package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class FriendshipInvitation {
    private Long id;
    private Long whoSendId;
    private Long whoReceiveId;
    private LocalDateTime sendTime;

    public FriendshipInvitation() {}

    public FriendshipInvitation(Long id, Long whoSendId, Long whoReceiveId, LocalDateTime sendTime) {
        this.id = id;
        this.whoSendId = whoSendId;
        this.whoReceiveId = whoReceiveId;
        this.sendTime = sendTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWhoSendId() {
        return whoSendId;
    }

    public void setWhoSendId(Long whoSendId) {
        this.whoSendId = whoSendId;
    }

    public Long getWhoReceiveId() {
        return whoReceiveId;
    }

    public void setWhoReceiveId(Long whoReceiveId) {
        this.whoReceiveId = whoReceiveId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
