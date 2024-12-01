package com.drgym.drgym.service;

import com.drgym.drgym.model.Training;
import com.drgym.drgym.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;

    public Optional<Training> findById(Long id) {return trainingRepository.findById(id);}
}
