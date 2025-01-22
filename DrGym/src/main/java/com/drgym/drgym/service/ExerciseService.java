package com.drgym.drgym.service;

import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.Clob;
import java.util.stream.Collectors;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }

    public Clob getExercisesForUserInPeriod(String username, String startDate, String endDate) {
        return exerciseRepository.getExercisesForUserInPeriod(username, startDate, endDate);
    }

    public Clob getUserDailyExerciseCount(String username, String startDate, String endDate) {
        return exerciseRepository.getUserDailyExerciseCount(username, startDate, endDate);
    }

    public Map<String, List<ExerciseDTO>> getExercisesByType() {
        List<Exercise> exercises = exerciseRepository.findAll();
        Map<String, List<ExerciseDTO>> exercisesByType = new HashMap<>();
        exercisesByType.put("strength", exercises.stream()
                .filter(exercise -> exercise.getType() == 'S')
                .map(exercise -> new ExerciseDTO(exercise.getId(), exercise.getName(), exercise.getVideoId()))
                .collect(Collectors.toList()));
        exercisesByType.put("cardio", exercises.stream()
                .filter(exercise -> exercise.getType() == 'C')
                .map(exercise -> new ExerciseDTO(exercise.getId(), exercise.getName(), exercise.getVideoId()))
                .collect(Collectors.toList()));
        exercisesByType.put("crossfit", exercises.stream()
                .filter(exercise -> exercise.getType() == 'F')
                .map(exercise -> new ExerciseDTO(exercise.getId(), exercise.getName(), exercise.getVideoId()))
                .collect(Collectors.toList()));
        return exercisesByType;
    }

    public Map<String, List<ExerciseRanking>> getExercisesWithRankingForUser(String username) {
        List<Exercise> exercises = exerciseRepository.findExercisesWithRanking(username);
        Map<String, List<ExerciseRanking>> exercisesByType = new HashMap<>();
        List<ExerciseRanking> strengthExercises = exercises.stream()
                .filter(exercise -> exercise.getType() == 'S')
                .map(exercise -> new ExerciseRanking(exercise.getId(), exercise.getName()))
                .collect(Collectors.toList());
        List<ExerciseRanking> crossfitExercises = exercises.stream()
                .filter(exercise -> exercise.getType() == 'F')
                .map(exercise -> new ExerciseRanking(exercise.getId(), exercise.getName()))
                .collect(Collectors.toList());
        exercisesByType.put("strength", strengthExercises);
        exercisesByType.put("crossfit", crossfitExercises);
        return exercisesByType;
    }

    public ResponseEntity<?> createExercise(@RequestBody Exercise request) {
        exerciseRepository.save(request);
        return ResponseEntity.ok("Exercise created successfully");
    }

    public Clob getExerciseRanking(String username, int exerciseId) {
        return exerciseRepository.getExerciseRanking(username, exerciseId);
    }

    public static class ExerciseDTO {
        private Long id;
        private String name;
        private String videoId;

        public ExerciseDTO(Long id, String name, String videoId) {
            this.id = id;
            this.name = name;
            this.videoId = videoId;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getVideoId() { return videoId; }
    }

    public static class ExerciseRanking {
        private Long id;
        private String name;

        public ExerciseRanking(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
