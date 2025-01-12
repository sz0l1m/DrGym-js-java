package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository <Activity, Long> {
    List<Activity> findByWorkoutId(Long workoutId);
    void deleteByWorkoutId(Long workoutId);
}
