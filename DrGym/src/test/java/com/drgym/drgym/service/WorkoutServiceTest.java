package com.drgym.drgym.service;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.UserRepository;
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
class WorkoutServiceTest {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    private Workout workout;
    private Activity activity;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setVerified(true);
        userRepository.save(user);

        workout = new Workout();
        workout.setStartDate(LocalDateTime.now());
        workout.setUsername("testuser");
        workout.setEndDate(LocalDateTime.now().plusHours(1));
        workout.setDescription("Test Workout");
        workout = workoutRepository.save(workout);

        activity = new Activity();
        activity.setExerciseId(1L);
        activity.setReps(10L);
        activity.setWeight(50L);
        activity.setDuration(Timestamp.valueOf(LocalDateTime.now()));
        activity.setWorkoutId(workout.getId());
        activity = activityRepository.save(activity);
    }

    @AfterEach
    void tearDown() {
        if (activity != null && activity.getId() != null) {
            activityRepository.deleteById(activity.getId());
        }
        if (workout != null && workout.getId() != null) {
            workoutRepository.deleteById(workout.getId());
        }
        if (user != null && user.getUsername() != null) {
            userRepository.deleteById(user.getUsername());
        }
    }

    @Test
    void testFindById() {
        Optional<Workout> foundWorkout = workoutService.findById(workout.getId());
        assertTrue(foundWorkout.isPresent());
        assertEquals(workout.getId(), foundWorkout.get().getId());
    }

    @Test
    void testFindActivitiesByWorkoutId() {
        List<Activity> activities = workoutService.findActivitiesByWorkoutId(workout.getId());
        assertFalse(activities.isEmpty());
        assertEquals(activity.getId(), activities.get(0).getId());
    }

    @Test
    void testSaveWorkout() {
        User newUser = new User();
        newUser.setUsername("testuser2");
        newUser.setName("Test");
        newUser.setSurname("User");
        newUser.setEmail("testuser2@example.com");
        newUser.setPassword("password");
        newUser.setVerified(true);
        userRepository.save(newUser);

        Workout newWorkout = new Workout();
        newWorkout.setStartDate(LocalDateTime.now());
        newWorkout.setUsername("testuser2");
        newWorkout.setEndDate(LocalDateTime.now().plusHours(1));
        newWorkout.setDescription("New Test Workout");

        workoutService.saveWorkout(newWorkout);
        assertNotNull(newWorkout.getId());
    }

    @Test
    void testDeleteWorkout() {
        workoutService.deleteWorkout(workout.getId());
        Optional<Workout> deletedWorkout = workoutRepository.findById(workout.getId());
        assertFalse(deletedWorkout.isPresent());
    }

    @Test
    void testUpdateWorkout() {
        workout.setDescription("Updated Description");
        workoutService.saveWorkout(workout);

        Optional<Workout> updatedWorkout = workoutRepository.findById(workout.getId());
        assertTrue(updatedWorkout.isPresent());
        assertEquals("Updated Description", updatedWorkout.get().getDescription());
    }
}