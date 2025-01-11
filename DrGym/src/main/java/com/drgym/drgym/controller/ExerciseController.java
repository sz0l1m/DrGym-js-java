package com.drgym.drgym.controller;

import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.model.ExerciseCreateRequest;
import com.drgym.drgym.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/by-type")
    public ResponseEntity<Map<String, List<ExerciseService.ExerciseDTO>>> getExercisesByType() {
        Map<String, List<ExerciseService.ExerciseDTO>> exercisesByType = exerciseService.getExercisesByType();
        return ResponseEntity.ok(exercisesByType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseService.findById(id);
        return exercise.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addExercise(@RequestBody ExerciseCreateRequest request) {
        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setType(request.getType());
        exercise.setKcal_burned(request.getKcalBurned());
        exercise.setMusclesWorked(request.getMusclesWorked());

        exerciseService.save(exercise);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(@PathVariable Long id, @RequestBody Exercise updatedExercise) {
        Optional<Exercise> exerciseOptional = exerciseService.findById(id);
        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Exercise exercise = exerciseOptional.get();
        exercise.setName(updatedExercise.getName());
        exercise.setType(updatedExercise.getType());
        exercise.setKcal_burned(updatedExercise.getKcal_burned());
        exercise.setMusclesWorked(updatedExercise.getMusclesWorked());

        Exercise savedExercise = exerciseService.save(exercise);
        return ResponseEntity.ok(savedExercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseService.findById(id);
        if (exercise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        exerciseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
