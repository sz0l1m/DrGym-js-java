package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity(1L, 10L, 50L, Timestamp.valueOf("2025-01-20 10:10:10"), 1L);
    }

    @Test
    void testActivityConstructor() {
        assertEquals(1L, activity.getExerciseId());
        assertEquals(10L, activity.getReps());
        assertEquals(50L, activity.getWeight());
        assertEquals(Timestamp.valueOf("2025-01-20 10:10:10"), activity.getDuration());
        assertEquals(1L, activity.getWorkoutId());
    }

    @Test
    void testSetId() {
        activity.setId(2L);
        assertEquals(2L, activity.getId());
    }

    @Test
    void testSetExerciseId() {
        activity.setExerciseId(2L);
        assertEquals(2L, activity.getExerciseId());
    }

    @Test
    void testSetReps() {
        activity.setReps(20L);
        assertEquals(20L, activity.getReps());
    }

    @Test
    void testSetWeight() {
        activity.setWeight(60L);
        assertEquals(60L, activity.getWeight());
    }

    @Test
    void testSetDuration() {
        Timestamp newDuration = Timestamp.valueOf("2025-01-21 11:11:11");
        activity.setDuration(newDuration);
        assertEquals(newDuration, activity.getDuration());
    }

    @Test
    void testSetDurationString() {
        activity.setDuration("12:12:12");
        assertEquals("12:12:12.0", activity.getDuration().toString().substring(11));
    }

    @Test
    void testSetWorkoutId() {
        activity.setWorkoutId(2L);
        assertEquals(2L, activity.getWorkoutId());
    }

    @Test
    void testSetExerciseName() {
        activity.setExerciseName("Push Up");
        assertEquals("Push Up", activity.getExerciseName());
    }

    @Test
    void testSetExerciseType() {
        activity.setExerciseType('C');
        assertEquals('C', activity.getExerciseType());
    }
}