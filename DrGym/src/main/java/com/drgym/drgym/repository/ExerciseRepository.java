package com.drgym.drgym.repository;

import com.drgym.drgym.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Clob;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query(nativeQuery = true, value = "SELECT GET_USERS_EXERCISES_IN_PERIOD(:username, :startDate, :endDate) FROM dual")
    Clob getExercisesForUserInPeriod(String username, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT GET_USER_DAILY_EXERCISE_COUNT(:username, :startDate, :endDate) FROM dual")
    Clob getUserDailyExerciseCount(String username, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT GET_EXERCISE_RANKING(:username, :exerciseId) FROM dual")
    Clob getExerciseRanking(@Param("username") String username, @Param("exerciseId") int exerciseId);

    @Query(nativeQuery = true, value = "SELECT e.*\n" +
            "FROM EXERCISES e\n" +
            "WHERE e.TYPE IN ('S', 'F')\n" +
            "AND EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM ACTIVITIES a\n" +
            "    JOIN WORKOUTS w ON a.WORKOUT_ID = w.WORKOUT_ID\n" +
            "    WHERE a.EXERCISE_ID = e.EXERCISE_ID\n" +
            "    AND w.USERNAME = :username\n" +
            ")")
    List<Exercise> findExercisesWithRanking(@Param("username") String username);
}
