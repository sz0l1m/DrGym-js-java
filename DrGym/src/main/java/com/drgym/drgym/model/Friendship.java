// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class Friendship {
    private Integer id;
    private Integer friendId1;
    private Integer friendId2;
    private LocalDateTime createdAt;

    public Friendship() {}

    public Friendship(Integer id, Integer friendId1, Integer friendId2) {
        this.id = id;
        this.friendId1 = friendId1;
        this.friendId2 = friendId2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriendId1() {
        return friendId1;
    }

    public void setFriendId1(Integer friendId1) {
        this.friendId1 = friendId1;
    }

    public Integer getFriendId2() {
        return friendId2;
    }

    public void setFriendId2(Integer friendId2) {
        this.friendId2 = friendId2;
    }
}
