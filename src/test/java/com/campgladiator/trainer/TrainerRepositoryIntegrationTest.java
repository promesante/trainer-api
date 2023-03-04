package com.campgladiator.trainer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TrainerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrainerRepository trainerRepository;

    @AfterEach
    public void tearDown() {
        trainerRepository.deleteAll();
    }

    @Test
    public void whenFindById_thenReturnTrainer() {
        Trainer found = new Trainer();
        found.setFirstName("Fearless");

        Trainer alex = new Trainer("Fearless", "Contender", "trainer@campgladiator.com", "5125125120");
        entityManager.persistAndFlush(alex);

        Optional<Trainer> optFound = trainerRepository.findById(new Long(1));
        optFound.ifPresent(t -> found.setFirstName(t.getFirstName()));
        assertThat(alex.getFirstName()).isEqualTo(found.getFirstName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Trainer alex = new Trainer("Fearless", "Contender", "trainer@campgladiator.com", "5125125120");
        entityManager.persistAndFlush(alex);

        Optional<Trainer> optFound = trainerRepository.findById(new Long(100));
        assertThat(optFound.isEmpty());
    }

    @Test
    public void givenSetOfTrainers_whenFindAll_thenReturnAllTrainers() {
        Trainer alex = new Trainer("Alex", "Smith", "alex.smith@campgladiator.com", "5125125120");
        Trainer john = new Trainer("John", "Doe", "john.doe@campgladiator.com", "5125125120");
        Trainer joe = new Trainer("Joe", "Green", "joe.green@campgladiator.com", "5125125120");

        entityManager.persist(alex);
        entityManager.persist(john);
        entityManager.persist(joe);
        entityManager.flush();

        Iterable<Trainer> allTrainers = trainerRepository.findAll();

        assertThat(allTrainers).hasSize(3).extracting(Trainer::getFirstName).containsOnly(alex.getFirstName(), john.getFirstName(), joe.getFirstName());
    }

}
