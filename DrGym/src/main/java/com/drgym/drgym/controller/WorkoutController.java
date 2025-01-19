package com.drgym.drgym.controller;

import com.drgym.drgym.model.*;
import com.drgym.drgym.service.WorkoutService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserController userController;

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkout(@PathVariable Long id, HttpServletRequest request) {
        Optional<Workout> workoutOptional = workoutService.findById(id);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();
        String username = workout.getUsername();

        if (!userController.tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        List<Activity> activities = workoutService.findActivitiesByWorkoutId(id);
        workout.setActivities(activities);

        return ResponseEntity.ok(workout);
    }

    @GetMapping("/private")
    public ResponseEntity<?> getPrivateWorkouts(HttpServletRequest request) {
        String username = userController.getUsernameFromToken(request);
        if (userController.isTokenExpired(request) || username == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        List<Workout> privateWorkouts = workoutService.findPrivateWorkoutsByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        List<Workout> pastWorkouts = new ArrayList<>();

        for (Workout workout : privateWorkouts) {
            if (workout.getStartDate().isBefore(now)) {
                pastWorkouts.add(workout);
            }
        }

        return ResponseEntity.ok(pastWorkouts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreateRequest workoutRequest, HttpServletRequest request) {
        String tokenUsername = userController.getUsernameFromToken(request);
        if (userController.isTokenExpired(request) || !tokenUsername.equals(workoutRequest.getUsername())) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        return workoutService.createWorkout(workoutRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id, HttpServletRequest request) {
        Optional<Workout> workoutOptional = workoutService.findById(id);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();
        String username = workout.getUsername();

        if (!userController.tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        return workoutService.deleteWorkout(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateWorkout(@RequestBody WorkoutUpdateRequest workoutRequest, HttpServletRequest request) {
        String tokenUsername = userController.getUsernameFromToken(request);
        if (userController.isTokenExpired(request) || !tokenUsername.equals(workoutRequest.getUsername())) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        return workoutService.updateWorkout(workoutRequest);
    }
}
