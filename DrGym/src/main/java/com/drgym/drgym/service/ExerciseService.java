package com.drgym.drgym.service;

import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
