package com.drgym.drgym.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private Exercise exercise;
    private List<String> musclesWorked;

    @BeforeEach
    void setUp() {
        musclesWorked = Arrays.asList("Biceps", "Triceps");
        exercise = new Exercise('C', 300L, "Push Up", musclesWorked);
    }

    @Test
    void testExerciseConstructor() {
        assertEquals('C', exercise.getType());
        assertEquals(300L, exercise.getKcalBurned());
        assertEquals("Push Up", exercise.getName());
        assertEquals(musclesWorked, exercise.getMusclesWorked());
    }

    @Test
    void testSetId() {
        exercise.setId(1L);
        assertEquals(1L, exercise.getId());
    }

    @Test
    void testSetType() {
        exercise.setType('A');
        assertEquals('A', exercise.getType());
    }

    @Test
    void testSetKcalBurned() {
        exercise.setKcalBurned(400L);
        assertEquals(400L, exercise.getKcalBurned());
    }

    @Test
    void testSetName() {
        exercise.setName("Pull Up");
        assertEquals("Pull Up", exercise.getName());
    }

    @Test
    void testSetMusclesWorked() {
        List<String> newMusclesWorked = Arrays.asList("Chest", "Back");
        exercise.setMusclesWorked(newMusclesWorked);
        assertEquals(newMusclesWorked, exercise.getMusclesWorked());
    }
}