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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Activity testActivity;
    private Workout testWorkout;
    private User testUser;

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

        testWorkout = new Workout(LocalDateTime.now(), testUser.getUsername(), LocalDateTime.now().plusHours(1), "Test Description", LocalDateTime.now());
        workoutRepository.save(testWorkout);

        testActivity = new Activity(1L, 10L, 20L, Timestamp.valueOf(LocalDateTime.now()), testWorkout.getId());
        activityRepository.save(testActivity);
    }

    @AfterEach
    void tearDown() {
        if (testActivity != null) {
            activityRepository.delete(testActivity);
        }
        if (testWorkout != null) {
            workoutRepository.delete(testWorkout);
        }
        if (testUser != null) {
            userRepository.deleteById(testUser.getUsername());
        }
    }

    @Test
    void testFindByWorkoutId() {
        List<Activity> activities = activityRepository.findByWorkoutId(testWorkout.getId());
        assertFalse(activities.isEmpty());
        assertEquals(testActivity.getId(), activities.get(0).getId());
    }
}