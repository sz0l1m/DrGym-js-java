package com.drgym.drgym.repository;

import com.drgym.drgym.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository <Workout, Long>{
}
