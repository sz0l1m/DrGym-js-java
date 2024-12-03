package com.drgym.drgym.repository;

import com.drgym.drgym.model.WorkoutActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutActivityRepository extends JpaRepository <WorkoutActivity, Long> {
    List<WorkoutActivity> findByWorkoutId(Long workoutId);

    List<WorkoutActivity> findByActivityId(Long activityId);
}
