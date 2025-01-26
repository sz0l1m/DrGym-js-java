package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Workout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Workout testWorkout;
    private User testUser;
    private Activity testActivity;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setVerified(true);
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        if (testWorkout != null) {
            workoutRepository.deleteById(testWorkout.getId());
        }
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
        if (testActivity != null) {
            activityRepository.deleteById(testActivity.getId());
        }
    }

    @Test
    void testFindByUsername() {
        testWorkout = new Workout();
        testWorkout.setUsername("testuser");
        workoutRepository.save(testWorkout);

        List<Workout> workouts = workoutRepository.findByUsername("testuser", null);
        assertFalse(workouts.isEmpty());
    }

    @Test
    void testFindByUsernameAndIsPostedFalse() {
        testWorkout = new Workout();
        testWorkout.setUsername("testuser");
        testWorkout.setPosted(false);
        workoutRepository.save(testWorkout);

        List<Workout> workouts = workoutRepository.findByUsernameAndIsPostedFalse("testuser", null);
        assertFalse(workouts.isEmpty());
    }

    @Test
    void testFindActivitiesByWorkoutId() {
        testWorkout = new Workout();
        testWorkout.setUsername("testuser");
        workoutRepository.save(testWorkout);

        testActivity = new Activity();
        testActivity.setWorkoutId(testWorkout.getId());
        testActivity.setExerciseId(1L);
        activityRepository.save(testActivity);

        List<Activity> activities = workoutRepository.findActivitiesByWorkoutId(testWorkout.getId());
        assertFalse(activities.isEmpty());
    }
}