package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostReactionTest {

    private PostReaction postReaction;

    @BeforeEach
    void setUp() {
        postReaction = new PostReaction(1L, "testUser");
    }

    @Test
    void testPostReactionConstructor() {
        assertEquals(1L, postReaction.getPostId());
        assertEquals("testUser", postReaction.getAuthorUsername());
    }

    @Test
    void testSetPostReactionId() {
        postReaction.setPostReactionId(2L);
        assertEquals(2L, postReaction.getPostReactionId());
    }

    @Test
    void testSetPostId() {
        postReaction.setPostId(2L);
        assertEquals(2L, postReaction.getPostId());
    }

    @Test
    void testSetAuthorUsername() {
        postReaction.setAuthorUsername("updatedUser");
        assertEquals("updatedUser", postReaction.getAuthorUsername());
    }
}