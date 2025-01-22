package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Workout;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutRepository extends JpaRepository <Workout, Long>{
    List<Workout> findByUsername(String username, Sort sort);
    List<Workout> findByUsernameAndIsPostedFalse(String username, Sort sort);
    @Query("SELECT a FROM Activity a WHERE a.workoutId = :workoutId")
    List<Activity> findActivitiesByWorkoutId(@Param("workoutId") Long workoutId);
    @Query("SELECT w FROM Workout w WHERE w.schedule > 0 AND w.startDate <= :endOfDay")
    List<Workout> findWorkoutsToSchedule(@Param("endOfDay") LocalDateTime endOfDay);
}
