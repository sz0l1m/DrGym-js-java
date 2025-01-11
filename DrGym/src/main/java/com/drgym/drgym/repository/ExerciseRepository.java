package com.drgym.drgym.repository;

import com.drgym.drgym.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Clob;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(nativeQuery = true, value = "SELECT GET_USERS_EXERCISES_IN_PERIOD(:username, :startDate, :endDate) FROM dual")
    Clob getExercisesForUserInPeriod(String username, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT GET_USER_DAILY_EXERCISE_COUNT(:username) FROM dual")
    Clob getUserDailyExerciseCount(String username);
}
