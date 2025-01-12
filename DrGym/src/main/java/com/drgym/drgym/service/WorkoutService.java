package com.drgym.drgym.service;

import com.drgym.drgym.model.*;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.WorkoutActivityRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private WorkoutActivityRepository workoutActivityRepository;

    public Optional<Workout> findById(Long id) {return workoutRepository.findById(id);}

    public List<Activity> findActivitiesByWorkoutId(Long workoutId) {
        List<WorkoutActivity> links = workoutActivityRepository.findByWorkoutId(workoutId);
        List<Long> activityIds = links.stream()
                .map(WorkoutActivity::getActivityId)
                .toList();
        return activityRepository.findAllById(activityIds);
    }

    public List<Workout> findByUsername(String username) {
        return workoutRepository.findByUsername(username);
    }

    public Workout saveWorkout(Workout workout) {
        List<Long> validActivityIds = workout.getActivityIds().stream()
                .filter(activityId -> activityRepository.existsById(activityId))
                .collect(Collectors.toList());

        workout.setActivityIds(validActivityIds);

        return workoutRepository.save(workout);
    }

    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreateRequest request) {
        List<Activity> savedActivities = activityRepository.saveAll(request.getActivities());

        Workout workout = new Workout(
                request.getStartDatetime(),
                request.getUsername(),
                request.getEndDatetime(),
                request.getDescription(),
                LocalDateTime.now()
        );
        Workout savedWorkout = workoutRepository.save(workout);

        List<WorkoutActivity> workoutActivities = savedActivities.stream()
                .map(activity -> new WorkoutActivity(savedWorkout.getId(), activity.getId()))
                .collect(Collectors.toList());
        workoutActivityRepository.saveAll(workoutActivities);

        return ResponseEntity.ok("Workout created successfully");
    }

    public void deleteWorkout(Long id) {
        List<WorkoutActivity> workoutActivities = workoutActivityRepository.findByWorkoutId(id);

        List<Long> activityIds = workoutActivities.stream()
                .map(WorkoutActivity::getActivityId)
                .toList();

        workoutActivityRepository.deleteAll(workoutActivities);
        activityRepository.deleteAllById(activityIds);

        workoutRepository.deleteById(id);
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
        workout.setDateStart(request.getStartDatetime());
        workout.setDateEnd(request.getEndDatetime());

        if (request.getActivitiesToRemove() != null) {
            activityRepository.deleteAllById(request.getActivitiesToRemove());
        }

        List<Activity> savedNewActivities = activityRepository.saveAll(request.getActivitiesToAdd());
        List<Long> newActivityIds = savedNewActivities.stream()
                .map(Activity::getId)
                .toList();

        List<Long> updatedActivityIds = workout.getActivityIds();
        if (request.getActivitiesToRemove() != null) {
            updatedActivityIds.removeAll(request.getActivitiesToRemove());
        }
        updatedActivityIds.addAll(newActivityIds);
        workout.setActivityIds(updatedActivityIds);

        workoutRepository.save(workout);

        List<WorkoutActivity> workoutActivities = savedNewActivities.stream()
                .map(activity -> new WorkoutActivity(workout.getId(), activity.getId()))
                .collect(Collectors.toList());
        workoutActivityRepository.saveAll(workoutActivities);

        return ResponseEntity.ok("Workout updated successfully");
    }
}
