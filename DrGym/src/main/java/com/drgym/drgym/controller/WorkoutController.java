package com.drgym.drgym.controller;

import com.drgym.drgym.model.*;
import com.drgym.drgym.service.ExerciseService;
import com.drgym.drgym.service.WorkoutService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

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

        WorkoutResponse response = new WorkoutResponse(
                workout.getId(),
                workout.getStartDate(),
                workout.getUsername(),
                workout.getEndDate(),
                workout.getDescription(),
                activities.stream()
                        .map(a -> {
                            String exerciseName = exerciseService.findById(a.getExerciseId())
                                    .map(Exercise::getName)
                                    .orElse("Unknown Exercise");

                            return new ActivityResponse(
                                    a.getId(),
                                    a.getExerciseId(),
                                    exerciseName,
                                    a.getDuration(),
                                    a.getWeight(),
                                    a.getReps()
                            );
                        })
                        .toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreateRequest workoutRequest, HttpServletRequest request) {
        if (userController.isTokenExpired(request)) {
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

        String username = workoutRequest.getUsername();

        if (!userController.tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        return workoutService.updateWorkout(workoutRequest);
    }

    public record WorkoutResponse(
            Long workoutId,
            LocalDateTime startDate,
            String username,
            LocalDateTime endDate,
            String description,
            List<ActivityResponse> activities
    ) {}

    public record ActivityResponse(
            Long activityId,
            Long exerciseId,
            String exerciseName,
            LocalTime duration,
            Long weight,
            Long reps
    ) {
        public ActivityResponse(
                Long activityId,
                Long exerciseId,
                String exerciseName,
                Timestamp duration,
                Long weight,
                Long reps
        ) {
            this(
                    activityId,
                    exerciseId,
                    exerciseName,
                    (duration != null) ? duration.toLocalDateTime().toLocalTime() : null,
                    weight,
                    reps
            );
        }
    }
}
