package com.drgym.drgym.repository;

import com.drgym.drgym.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Clob;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(nativeQuery = true, value = "SELECT GET_USERS_EXERCISES_IN_PERIOD(:username, :startDate, :endDate) FROM dual")
    Clob getExercisesForUserInPeriod(String username, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT GET_USER_DAILY_EXERCISE_COUNT(:username, :startDate, :endDate) FROM dual")
    Clob getUserDailyExerciseCount(String username, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT GET_EXERCISE_RANKING(:username, :exerciseId) FROM dual")
    Clob getExerciseRanking(@Param("username") String username, @Param("exerciseId") int exerciseId);
}
