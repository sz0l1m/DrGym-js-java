package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

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
                workout.getDateEnd(),
                workout.getDescription(),
                activities.stream()
                        .map(a -> new ActivityResponse(
                                a.getId(),
                                a.getExercise_id(),
                                a.getDuration(),
                                a.getWeight(),
                                a.getSets()))
                        .toList());

        return ResponseEntity.ok(response);
    }

    private record TrainingDTO(Long id, LocalDateTime dateStart, LocalDateTime dateEnd, String description, LocalDateTime create_datetime) {}



    public record WorkoutResponse(
            Long workoutId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String description,
            List<ActivityResponse> activities
    ) {}

    public record ActivityResponse(
            Long activityId,
            Long exerciseId,
            Timestamp duration,
            Long weight,
            Long sets
    ) {}

}
