package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.ExerciseRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ExerciseRepository exerciseRepository;

    public Optional<Workout> findById(Long id) {
        Optional<Workout> workoutOptional = workoutRepository.findById(id);
        if (workoutOptional.isPresent()) {
            Workout workout = workoutOptional.get();
            List<Activity> activities = activityRepository.findByWorkoutId(workout.getId());
            activities.forEach(activity -> {
                Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
                if (exercise != null) {
                    activity.setExerciseName(exercise.getName());
                }
            });
            workout.setActivities(activities);
        }
        return workoutOptional;
    }

    public List<Activity> findActivitiesByWorkoutId(Long workoutId) {
        List<Activity> activities = activityRepository.findByWorkoutId(workoutId);
        activities.forEach(activity -> {
            Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
            if (exercise != null) {
                activity.setExerciseName(exercise.getName());
            }
            activity.setExerciseType(exercise.getType());
        });
        return activities;
    }

    public List<Workout> findByUsername(String username) {
        return workoutRepository.findByUsername(username, Sort.by(Sort.Direction.DESC, "startDate"));
    }

    public void saveWorkout(Workout workout) {
        workoutRepository.save(workout);
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
                LocalDateTime.now(),
                request.getSchedule()
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

        workout.setSchedule(request.getSchedule());

        workoutRepository.save(workout);

        return ResponseEntity.ok("Workout updated successfully");
    }

    public List<Workout> findPrivateWorkoutsByUsername(String username) {
        List<Workout> workouts = workoutRepository.findByUsernameAndIsPostedFalse(username, Sort.by(Sort.Direction.DESC, "startDate"));
        workouts.forEach(workout -> {
            List<Activity> activities = activityRepository.findByWorkoutId(workout.getId());
            activities.forEach(activity -> {
                Exercise exercise = exerciseRepository.findById(activity.getExerciseId()).orElse(null);
                if (exercise != null) {
                    activity.setExerciseName(exercise.getName());
                }
                activity.setExerciseType(exercise.getType());
            });
            workout.setActivities(activities);
        });
        return workouts;
    }
}