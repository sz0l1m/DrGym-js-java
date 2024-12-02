package com.drgym.drgym.controller;

import com.drgym.drgym.model.Training;
import com.drgym.drgym.service.TrainingService;
import com.drgym.drgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("api/workout")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraining(@PathVariable Long id) {
        Optional<Training> training = trainingService.findById(id);
        return training.map(t -> ResponseEntity.ok(new TrainingDTO(t.getId(), t.getDateStart(), t.getDateEnd(), t.getDescription(), t.getDateCreated())))
                .orElse(ResponseEntity.notFound().build());
    }

    private record TrainingDTO(Long id, LocalDateTime dateStart, LocalDateTime dateEnd, String description, LocalDateTime create_datetime) {}
}
