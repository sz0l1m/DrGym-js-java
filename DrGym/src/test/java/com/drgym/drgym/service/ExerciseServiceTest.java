package com.drgym.drgym.service;

import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.repository.ExerciseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ExerciseServiceTest {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise('S', 300L, "Push Up", Arrays.asList("Biceps", "Triceps"));
    }

    @AfterEach
    void tearDown() {
        if (exercise != null && exercise.getId() != null) {
            exerciseRepository.deleteById(exercise.getId());
        }
    }

    @Test
    void testAddAndRemoveExercise() {
        Exercise savedExercise = exerciseService.save(exercise);
        assertNotNull(savedExercise);
        assertEquals(exercise.getName(), savedExercise.getName());

        Optional<Exercise> foundExercise = exerciseService.findById(savedExercise.getId());
        assertTrue(foundExercise.isPresent());
        assertEquals(savedExercise.getName(), foundExercise.get().getName());

        exerciseService.deleteById(savedExercise.getId());

        Optional<Exercise> deletedExercise = exerciseService.findById(savedExercise.getId());
        assertFalse(deletedExercise.isPresent());
    }

    @Test
    void testFindAll() {
        Exercise savedExercise = exerciseService.save(exercise);
        assertNotNull(savedExercise);

        List<Exercise> exercises = exerciseService.findAll();
        assertFalse(exercises.isEmpty());
        assertTrue(exercises.stream().anyMatch(e -> e.getId().equals(savedExercise.getId())));
    }

    @Test
    void testUpdateExercise() {
        Exercise savedExercise = exerciseService.save(exercise);
        assertNotNull(savedExercise);

        savedExercise.setName("Updated Push Up");
        savedExercise.setMusclesWorked(new ArrayList<>(Arrays.asList("Chest", "Triceps")));
        Exercise updatedExercise = exerciseService.save(savedExercise);

        Optional<Exercise> foundExercise = exerciseService.findById(updatedExercise.getId());
        assertTrue(foundExercise.isPresent());
        assertEquals("Updated Push Up", foundExercise.get().getName());
    }
}