package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.service.ActivityService;
import com.drgym.drgym.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable Long id) {
        Optional<Activity> activityOptional = activityService.findById(id);

        if (activityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Activity activity = activityOptional.get();

        Optional<Exercise> exerciseOptional = exerciseService.findById(activity.getExerciseId());

        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Exercise exercise = exerciseOptional.get();

        ActivityDTO activityDTO = new ActivityDTO(
                activity.getId(),
                activity.getExerciseId(),
                exercise.getName(),
                activity.getDuration() != null ? activity.getDuration().toLocalDateTime().toLocalTime() : null,
                activity.getWeight(),
                activity.getReps()
        );

        return ResponseEntity.ok(activityDTO);
    }

    @GetMapping
    public List<Activity> getAllActivitiesById(@RequestParam List<Long> ids) {
        return activityService.findAllById(ids);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    public record ActivityDTO(
            Long activityId,
            Long exerciseId,
            String exerciseName,
            LocalTime duration,
            Long weight,
            Long reps
    ) {
        public ActivityDTO(
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