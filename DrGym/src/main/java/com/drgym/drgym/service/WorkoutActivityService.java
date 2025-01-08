package com.drgym.drgym.service;

import com.drgym.drgym.model.WorkoutActivity;
import com.drgym.drgym.repository.WorkoutActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutActivityService {
    @Autowired
    private WorkoutActivityRepository workoutActivityRepository;

    public List<WorkoutActivity> findByWorkoutId(Long workoutId) {
        return workoutActivityRepository.findByWorkoutId(workoutId);
    }

    public List<WorkoutActivity> findByActivityId(Long activityId) {
        return workoutActivityRepository.findByActivityId(activityId);
    }

    public WorkoutActivity saveWorkoutActivity(WorkoutActivity workoutActivity) {
        return workoutActivityRepository.save(workoutActivity);
    }

    public void addActivityToWorkout(Long workoutId, Long activityId) {
        WorkoutActivity workoutActivity = new WorkoutActivity();
        workoutActivity.setWorkoutId(workoutId);
        workoutActivity.setActivityId(activityId);
        workoutActivityRepository.save(workoutActivity);
    }

    public void deleteWorkoutActivity(Long id) {
        workoutActivityRepository.deleteById(id);
    }
}