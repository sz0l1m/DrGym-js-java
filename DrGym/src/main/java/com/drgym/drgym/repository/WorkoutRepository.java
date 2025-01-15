package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutRepository extends JpaRepository <Workout, Long>{
    List<Workout> findByUsername(String username);
    @Query("SELECT a FROM Activity a WHERE a.workoutId = :workoutId")
    List<Activity> findActivitiesByWorkoutId(@Param("workoutId") Long workoutId);
}
