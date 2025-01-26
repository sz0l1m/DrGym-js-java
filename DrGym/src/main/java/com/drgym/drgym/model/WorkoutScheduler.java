package com.drgym.drgym.model;

import com.drgym.drgym.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WorkoutScheduler {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleNewWorkouts() {
        processWorkouts();
    }

    @PostConstruct
    public void processWorkoutsOnStartup() {
        processWorkouts();
    }

    private void processWorkouts() {
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        List<Workout> workouts = workoutRepository.findWorkoutsToSchedule(endOfDay);

        for (Workout workout : workouts) {
            Workout newWorkout = new Workout();
            newWorkout.setStartDate(workout.getStartDate().plusDays(workout.getSchedule()));
            newWorkout.setEndDate(workout.getEndDate().plusDays(workout.getSchedule()));
            newWorkout.setUsername(workout.getUsername());
            newWorkout.setDescription(workout.getDescription());
            newWorkout.setDateCreated(LocalDateTime.now());
            newWorkout.setPosted(false);
            newWorkout.setSchedule(workout.getSchedule());
            workoutRepository.save(newWorkout);

            workout.setSchedule(0);
            workoutRepository.save(workout);
        }
    }
}