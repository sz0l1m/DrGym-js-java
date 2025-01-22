package com.drgym.drgym.service;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    private Activity activity;
    private Workout workout;

    @BeforeEach
    void setUp() {
        workout = new Workout();
        workout = workoutRepository.save(workout);

        activity = new Activity();
        activity.setExerciseId(1L);
        activity.setReps(10L);
        activity.setWeight(50L);
        activity.setDuration(Timestamp.valueOf(LocalDateTime.now()));
        activity.setWorkoutId(workout.getId());
    }

    @AfterEach
    void tearDown() {
        if (activity != null && activity.getId() != null) {
            activityRepository.deleteById(activity.getId());
        }
        if (workout != null && workout.getId() != null) {
            workoutRepository.deleteById(workout.getId());
        }
    }

    @Test
    void testAddAndRemoveActivity() {
        Activity savedActivity = activityService.saveActivity(activity);
        assertNotNull(savedActivity);
        assertEquals(activity.getExerciseId(), savedActivity.getExerciseId());

        Optional<Activity> foundActivity = activityService.findById(savedActivity.getId());
        assertTrue(foundActivity.isPresent());
        assertEquals(savedActivity.getExerciseId(), foundActivity.get().getExerciseId());

        activityService.deleteActivity(savedActivity.getId());

        Optional<Activity> deletedActivity = activityService.findById(savedActivity.getId());
        assertFalse(deletedActivity.isPresent());
    }

    @Test
    void testFindAllById() {
        Activity savedActivity = activityService.saveActivity(activity);
        assertNotNull(savedActivity);

        List<Activity> activities = activityService.findAllById(List.of(savedActivity.getId()));
        assertFalse(activities.isEmpty());
        assertEquals(savedActivity.getId(), activities.get(0).getId());
    }

    @Test
    void testUpdateActivity() {
        Activity savedActivity = activityService.saveActivity(activity);
        assertNotNull(savedActivity);

        savedActivity.setReps(15L);
        savedActivity.setWeight(60L);
        Activity updatedActivity = activityService.saveActivity(savedActivity);

        Optional<Activity> foundActivity = activityService.findById(updatedActivity.getId());
        assertTrue(foundActivity.isPresent());
        assertEquals(15L, foundActivity.get().getReps());
        assertEquals(60L, foundActivity.get().getWeight());
    }

    @Test
    void testDeleteActivitiesByWorkoutId() {
        Activity savedActivity = activityService.saveActivity(activity);
        assertNotNull(savedActivity);

        activityService.deleteActivitiesByWorkoutId(workout.getId());

        Optional<Activity> deletedActivity = activityService.findById(savedActivity.getId());
        assertFalse(deletedActivity.isPresent());
    }
}