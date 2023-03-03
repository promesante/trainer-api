package com.campgladiator.trainer;

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
    Trainer postTrainer(@RequestBody Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @GetMapping
    Iterable<Trainer> getTrainers() {
        return trainerRepository.findAll();
    }

}
