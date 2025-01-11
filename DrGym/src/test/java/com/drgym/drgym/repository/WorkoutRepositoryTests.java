package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.WorkoutActivity;
import com.drgym.drgym.service.WorkoutActivityService;
import com.drgym.drgym.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutActivityRepository workoutActivityRepository;

    @Autowired
    private WorkoutActivityService workoutActivityService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private WorkoutService workoutService;

    private Workout workout;


//    @Test
//    void testSaveAndFindWorkout() {
//        LocalDateTime start = LocalDateTime.of(2024, 12, 12, 12, 12);
//        LocalDateTime end = LocalDateTime.of(2024, 12, 12, 13, 12);
//        LocalDateTime created = LocalDateTime.of(2024, 12, 13, 12, 12);
//
//        Workout workout = new Workout(start, "skuter", end, "test", created);
//        Workout savedWorkout = workoutRepository.save(workout);
//
//        Activity activity = new Activity(2137L, 1L, 10L, 10L, new Timestamp(0));
//        Activity savedActivity = activityRepository.save(activity);
//
//        workoutService.addActivityToWorkout(savedWorkout.getId(), savedActivity.getId());
//
//        Workout updatedWorkout = workoutRepository.findById(savedWorkout.getId()).orElseThrow();
//        assertTrue(updatedWorkout.getActivityIds().contains(savedActivity.getId()));
//
//        workoutService.deleteWorkout(2137L);
//    }

//    @Test
//    void testRemoveWorkout() {
//        LocalDateTime start = LocalDateTime.of(2024, 12, 12, 12, 12);
//        LocalDateTime end = LocalDateTime.of(2024, 12, 12, 13, 12);
//        LocalDateTime created = LocalDateTime.of(2024, 12, 13, 12, 12);
//
//        Workout workout = new Workout(2137L, start, "skuter", end, "test", created);
//        Workout savedWorkout = workoutRepository.save(workout);
//
//        Activity activity = new Activity(2137L, 1L, 10L, 10L, new Timestamp(0));
//        Activity savedActivity = activityRepository.save(activity);
//
//        workoutService.addActivityToWorkout(savedWorkout.getId(), savedActivity.getId());
//
//        Workout updatedWorkout = workoutRepository.findById(savedWorkout.getId()).orElseThrow();
//        assertTrue(updatedWorkout.getActivityIds().contains(savedActivity.getId()));
//
//        workoutService.removeActivityFromWorkout(savedWorkout.getId(), savedActivity.getId());
//
//        updatedWorkout = workoutRepository.findById(savedWorkout.getId()).orElseThrow();
//        assertFalse(updatedWorkout.getActivityIds().contains(savedActivity.getId()));
//
//        workoutService.deleteWorkout(2137L);
//    }
}