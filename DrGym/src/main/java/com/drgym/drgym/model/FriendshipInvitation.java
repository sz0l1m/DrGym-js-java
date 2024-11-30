// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class FriendshipInvitation {
    private Integer id;
    private Integer whoSendId;
    private Integer whoReceiveId;
    private LocalDateTime sendTime;

    public FriendshipInvitation() {}

    public FriendshipInvitation(Integer id, Integer whoSendId, Integer whoReceiveId, LocalDateTime sendTime) {
        this.id = id;
        this.whoSendId = whoSendId;
        this.whoReceiveId = whoReceiveId;
        this.sendTime = sendTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWhoSendId() {
        return whoSendId;
    }

    public void setWhoSendId(Integer whoSendId) {
        this.whoSendId = whoSendId;
    }

    public Integer getWhoReceiveId() {
        return whoReceiveId;
    }

    public void setWhoReceiveId(Integer whoReceiveId) {
        this.whoReceiveId = whoReceiveId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
