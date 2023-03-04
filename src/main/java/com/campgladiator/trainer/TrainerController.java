package com.campgladiator.trainer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerRepository trainerRepository;

    public TrainerController(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @GetMapping("/{id}")
    Optional<Trainer> getTrainerById(@PathVariable String id) {
        return trainerRepository.findById(Long.getLong(id));
    }

    @PostMapping
    public ResponseEntity<Trainer> postTrainer(@RequestBody Trainer trainer) {
        HttpStatus status = HttpStatus.CREATED;
        Trainer saved =  trainerRepository.save(trainer);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping
    public Iterable<Trainer> getTrainers() {
        return trainerRepository.findAll();
    }

}
