package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    private Post post;
    private Workout workout;
    private List<PostReaction> reactions;

    @BeforeEach
    void setUp() {
        workout = new Workout();
        reactions = new ArrayList<>();
        post = new Post(1L, LocalDateTime.now(), "Test Title", "Test Content");
        post.setTraining(workout);
        post.setReactions(reactions);
    }

    @Test
    void testPostConstructor() {
        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
    }

    @Test
    void testSetId() {
        post.setId(2L);
        assertEquals(2L, post.getId());
    }

    @Test
    void testSetUsername() {
        post.setUsername("testUser");
        assertEquals("testUser", post.getUsername());
    }

    @Test
    void testSetDate() {
        LocalDateTime now = LocalDateTime.now();
        post.setDate(now);
        assertEquals(now, post.getDate());
    }

    @Test
    void testSetTitle() {
        post.setTitle("Updated Title");
        assertEquals("Updated Title", post.getTitle());
    }

    @Test
    void testSetContent() {
        post.setContent("Updated Content");
        assertEquals("Updated Content", post.getContent());
    }

    @Test
    void testSetTraining() {
        Workout newWorkout = new Workout();
        post.setTraining(newWorkout);
        assertEquals(newWorkout, post.getTraining());
    }

    @Test
    void testSetReactions() {
        List<PostReaction> newReactions = new ArrayList<>();
        post.setReactions(newReactions);
        assertEquals(newReactions, post.getReactions());
    }

    @Test
    void testAddReaction() {
        PostReaction reaction = new PostReaction();
        post.addReaction(reaction);
        assertTrue(post.getReactions().contains(reaction));
    }

    @Test
    void testRemoveReaction() {
        PostReaction reaction = new PostReaction();
        post.addReaction(reaction);
        post.removeReaction(reaction);
        assertFalse(post.getReactions().contains(reaction));
    }

    @Test
    void testSetWorkoutId() {
        post.setWorkoutId(3L);
        assertEquals(3L, post.getWorkoutId());
    }

    @Test
    void testSetReactionCount() {
        post.setReactionCount(5);
        assertEquals(5, post.getReactionCount());
    }

    @Test
    void testSetUserReaction() {
        post.setUserReaction(1);
        assertEquals(1, post.getUserReaction());
    }

    @Test
    void testSetAvatar() {
        post.setAvatar("avatar.png");
        assertEquals("avatar.png", post.getAvatar());
    }
}