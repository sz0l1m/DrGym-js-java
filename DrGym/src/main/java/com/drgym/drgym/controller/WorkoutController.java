package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.service.ExerciseService;
import com.drgym.drgym.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraining(@PathVariable Long id) {
        Optional<Workout> workoutOptional = workoutService.findById(id);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();
        List<Activity> activities = workoutService.findActivitiesByWorkoutId(id);

        WorkoutResponse response = new WorkoutResponse(
                workout.getId(),
                workout.getDateStart(),
                workout.getUsername(),
                workout.getDateEnd(),
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

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        Workout savedWorkout = workoutService.saveWorkout(workout);
        return ResponseEntity.ok(savedWorkout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
        Optional<Workout> workoutOptional = workoutService.findById(id);

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
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
