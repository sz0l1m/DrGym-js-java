package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FriendshipTest {

    private Friendship friendship;

    @BeforeEach
    void setUp() {
        friendship = new Friendship(1L, "user1", "user2");
    }

    @Test
    void testFriendshipConstructor() {
        assertEquals(1L, friendship.getId());
        assertEquals("user1", friendship.getFriend1Username());
        assertEquals("user2", friendship.getFriend2Username());
    }

    @Test
    void testSetId() {
        friendship.setId(2L);
        assertEquals(2L, friendship.getId());
    }

    @Test
    void testSetFriend1Username() {
        friendship.setFriend1Username("newUser1");
        assertEquals("newUser1", friendship.getFriend1Username());
    }

    @Test
    void testSetFriend2Username() {
        friendship.setFriend2Username("newUser2");
        assertEquals("newUser2", friendship.getFriend2Username());
    }

    @Test
    void testSetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        friendship.setCreatedAt(now);
        assertEquals(now, friendship.getCreatedAt());
    }
}