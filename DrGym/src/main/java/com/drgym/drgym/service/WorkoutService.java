package com.drgym.drgym.service;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.WorkoutActivity;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.WorkoutActivityRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Workout saveWorkout(Workout workout) {
        List<Long> validActivityIds = workout.getActivityIds().stream()
                .filter(activityId -> activityRepository.existsById(activityId))
                .collect(Collectors.toList());

        workout.setActivityIds(validActivityIds);

        return workoutRepository.save(workout);
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
    public void addActivityToWorkout(Long workoutId, Long activityId) {
        Optional<Workout> workoutOptional = workoutRepository.findById(workoutId);
        if (workoutOptional.isPresent() && activityRepository.existsById(activityId)) {
            Workout workout = workoutOptional.get();
            workout.getActivityIds().add(activityId);
            workoutRepository.save(workout);

            WorkoutActivity workoutActivity = new WorkoutActivity(2137L, workoutId, activityId);
            workoutActivityRepository.save(workoutActivity);
        }
    }

    public void removeActivityFromWorkout(Long workoutId, Long activityId) {
        Optional<Workout> workoutOptional = workoutRepository.findById(workoutId);
        if (workoutOptional.isPresent()) {
            Workout workout = workoutOptional.get();
            workout.getActivityIds().remove(activityId);
            workoutRepository.save(workout);

            WorkoutActivity workoutActivity = workoutActivityRepository.findByWorkoutIdAndActivityId(workoutId, activityId);
            if (workoutActivity != null) {
                workoutActivityRepository.delete(workoutActivity);
            }
        }
    }
}
