package com.drgym.drgym.service;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    public List<Activity> findAllById(List<Long> ids) {
        return activityRepository.findAllById(ids);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    public void deleteActivitiesByWorkoutId(Long workoutId) {
        activityRepository.deleteByWorkoutId(workoutId);
    }
}