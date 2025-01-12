package com.drgym.drgym.repository;

import com.drgym.drgym.model.Workout;
import com.drgym.drgym.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkoutRepositoryTests {

    @Autowired
    private WorkoutRepository workoutRepository;

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