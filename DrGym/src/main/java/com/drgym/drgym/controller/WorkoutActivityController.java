package com.drgym.drgym.controller;

import com.drgym.drgym.model.WorkoutActivity;
import com.drgym.drgym.service.WorkoutActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-activities")
public class WorkoutActivityController {
    @Autowired
    private WorkoutActivityService workoutActivityService;

    @GetMapping("/workout/{workoutId}")
    public List<WorkoutActivity> getWorkoutActivitiesByWorkoutId(@PathVariable Long workoutId) {
        return workoutActivityService.findByWorkoutId(workoutId);
    }

    @GetMapping("/activity/{activityId}")
    public List<WorkoutActivity> getWorkoutActivitiesByActivityId(@PathVariable Long activityId) {
        return workoutActivityService.findByActivityId(activityId);
    }

    @PostMapping
    public WorkoutActivity createWorkoutActivity(@RequestBody WorkoutActivity workoutActivity) {
        return workoutActivityService.saveWorkoutActivity(workoutActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutActivity(@PathVariable Long id) {
        workoutActivityService.deleteWorkoutActivity(id);
        return ResponseEntity.noContent().build();
    }
}