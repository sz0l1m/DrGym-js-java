package com.drgym.drgym.service;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.WorkoutActivity;
import com.drgym.drgym.repository.ActivityRepository;
import com.drgym.drgym.repository.WorkoutActivityRepository;
import com.drgym.drgym.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutActivityRepository.deleteByWorkoutId(id);

        List<Activity> activities = findActivitiesByWorkoutId(id);
        activityRepository.deleteAll(activities);

        workoutRepository.deleteById(id);
    }
}
