package com.drgym.drgym.repository;

import com.drgym.drgym.model.WorkoutActivity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutActivityRepository extends JpaRepository <WorkoutActivity, Long> {
    List<WorkoutActivity> findByWorkoutId(Long workoutId);

    List<WorkoutActivity> findByActivityId(Long activityId);

    WorkoutActivity findByWorkoutIdAndActivityId(Long workoutId, Long activityId);

    @Modifying
    @Transactional
    @Query("DELETE FROM WorkoutActivity wa WHERE wa.workoutId = :workoutId")
    void deleteByWorkoutId(@Param("workoutId") Long workoutId);
}
