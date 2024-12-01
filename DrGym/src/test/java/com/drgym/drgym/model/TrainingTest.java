// author: ksiemion
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;


public class TrainingTest {
    @Test
    public void trainingConstructorShouldInitializeFieldsCorrectly() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 24, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 24, 12, 0);
        Training training = new Training(start, end);
        assertEquals(start, training.getDateStart());
        assertEquals(end, training.getDateEnd());
        assertEquals(Duration.ofHours(2), training.getTotalTime());
    }

    @Test
    public void trainingConstructorShouldHandleZeroDuration() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 24, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 24, 10, 0);
        Training training = new Training(start, end);
        assertEquals(Duration.ZERO, training.getTotalTime());
    }

    @Test
    public void trainingConstructorShouldThrowExceptionIfStartIsAfterEnd() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 24, 12, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 24, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> new Training(start, end));
    }

    @Test
    public void addExerciseShouldAddExerciseToList() {
        Training training = new Training(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Exercise exercise = new StrengthExercise();
        training.addExercise(exercise.getId());
        assertTrue(training.getExercises().contains(exercise.getId()));
    }

    @Test
    public void addExerciseShouldNotAddDuplicateExercise() {
        Training training = new Training(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Exercise exercise = new StrengthExercise();
        training.addExercise(exercise.getId());
        training.addExercise(exercise.getId());
        assertEquals(1, training.getExercises().size());
    }

    @Test
    public void removeExerciseShouldRemoveExerciseFromList() {
        Training training = new Training(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Exercise exercise = new StrengthExercise();
        training.addExercise(exercise.getId());
        training.removeExercise(exercise.getId());
        assertFalse(training.getExercises().contains(exercise.getId()));
    }

    @Test
    public void clearExercisesShouldClearAllExercises() {
        Training training = new Training(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Exercise exercise1 = new StrengthExercise();
        Exercise exercise2 = new CardioExercise();
        training.addExercise(exercise1.getId());
        training.addExercise(exercise2.getId());
        training.clearExercises();
        assertTrue(training.getExercises().isEmpty());
    }
}
