package com.drgym.drgym.repository;

import com.drgym.drgym.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository <Workout, Long>{
    List<Workout> findByUsername(String username);
}
