package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private Workout workout;
    private List<Activity> activities;

    @BeforeEach
    void setUp() {
        activities = new ArrayList<>();
        workout = new Workout(LocalDateTime.now(), "testUser", LocalDateTime.now().plusHours(1), "Test Description", LocalDateTime.now());
        workout.setActivities(activities);
    }

    @Test
    void testWorkoutConstructor() {
        assertEquals("testUser", workout.getUsername());
        assertEquals("Test Description", workout.getDescription());
        assertNotNull(workout.getStartDate());
        assertNotNull(workout.getEndDate());
        assertNotNull(workout.getDateCreated());
    }

    @Test
    void testSetId() {
        workout.setId(1L);
        assertEquals(1L, workout.getId());
    }

    @Test
    void testSetStartDate() {
        LocalDateTime newStartDate = LocalDateTime.now().plusDays(1);
        workout.setStartDate(newStartDate);
        assertEquals(newStartDate, workout.getStartDate());
    }

    @Test
    void testSetEndDate() {
        LocalDateTime newEndDate = LocalDateTime.now().plusDays(1).plusHours(1);
        workout.setEndDate(newEndDate);
        assertEquals(newEndDate, workout.getEndDate());
    }

    @Test
    void testSetUsername() {
        workout.setUsername("updatedUser");
        assertEquals("updatedUser", workout.getUsername());
    }

    @Test
    void testSetDescription() {
        workout.setDescription("Updated Description");
        assertEquals("Updated Description", workout.getDescription());
    }

    @Test
    void testSetDateCreated() {
        LocalDateTime newDateCreated = LocalDateTime.now().plusDays(1);
        workout.setDateCreated(newDateCreated);
        assertEquals(newDateCreated, workout.getDateCreated());
    }

    @Test
    void testSetActivities() {
        List<Activity> newActivities = new ArrayList<>();
        workout.setActivities(newActivities);
        assertEquals(newActivities, workout.getActivities());
    }

    @Test
    void testSetPosted() {
        workout.setPosted(true);
        assertTrue(workout.isPosted());
    }
}