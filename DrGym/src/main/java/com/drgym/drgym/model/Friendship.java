// author: ksiemion
package com.drgym.drgym.model;

import java.time.LocalDateTime;

public class Friendship {
    private Long id;
    private Long friendId1;
    private Long friendId2;
    private LocalDateTime createdAt;

    public Friendship() {}

    public Friendship(Long id, Long friendId1, Long friendId2) {
        this.id = id;
        this.friendId1 = friendId1;
        this.friendId2 = friendId2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFriendId1() {
        return friendId1;
    }

    public void setFriendId1(Long friendId1) {
        this.friendId1 = friendId1;
    }

    public Long getFriendId2() {
        return friendId2;
    }

    public void setFriendId2(Long friendId2) {
        this.friendId2 = friendId2;
    }
}
