package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public Optional<Workout> findById(Long id) { return workoutRepository.findById(id); }

    public List<Activity> findActivitiesByWorkoutId(Long workoutId) {
        return activityRepository.findByWorkoutId(workoutId);
    }

    public List<Workout> findByUsername(String username) {
        return workoutRepository.findByUsername(username);
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreateRequest request) {
        if (request.getStartDate() == null) {
            return ResponseEntity.badRequest().body("Start date cannot be null");
        }

        Workout workout = new Workout(
                request.getStartDate(),
                request.getUsername(),
                request.getEndDate(),
                request.getDescription(),
                LocalDateTime.now()
        );
        Workout savedWorkout = workoutRepository.save(workout);

        List<Activity> activities = request.getActivities();
        activities.forEach(activity -> activity.setWorkoutId(savedWorkout.getId()));
        activityRepository.saveAll(activities);

        return ResponseEntity.ok("Workout created successfully");
    }

    @Transactional
    public ResponseEntity<?> deleteWorkout(Long id) {
        activityRepository.deleteByWorkoutId(id);
        workoutRepository.deleteById(id);
        return ResponseEntity.ok("Workout deleted successfully");
    }

    @Transactional
    public ResponseEntity<?> updateWorkout(@RequestBody WorkoutUpdateRequest request) {
        Optional<Workout> workoutOptional = workoutRepository.findById(request.getId());

        if (workoutOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Workout workout = workoutOptional.get();
        workout.setUsername(request.getUsername());
        workout.setDescription(request.getDescription());
        workout.setStartDate(request.getStartDate());
        workout.setEndDate(request.getEndDate());

        if (request.getActivitiesToRemove() != null) {
            activityRepository.deleteAllById(request.getActivitiesToRemove());
        }

        List<Activity> savedNewActivities = activityRepository.saveAll(request.getActivitiesToAdd());
        savedNewActivities.forEach(activity -> activity.setWorkoutId(workout.getId()));

        workoutRepository.save(workout);

        return ResponseEntity.ok("Workout updated successfully");
    }
}