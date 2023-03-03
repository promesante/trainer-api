package com.campgladiator.trainer;

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

    public void tearDown() {
        trainerRepository.deleteAll();
    }

    @Test
    public void whenFindById_thenReturnTrainer() {
        Trainer found = new Trainer();
        found.setFirstName("not found");

        Trainer alex = new Trainer("Fearless", "Contender", "trainer@campgladiator.com", "5125125120");
        entityManager.persistAndFlush(alex);

        Optional<Trainer> optFound = trainerRepository.findById(new Long(1));
        optFound.ifPresent(t -> found.setFirstName(t.getFirstName()));
        assertThat(alex.getFirstName()).isEqualTo(found.getFirstName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        Trainer alex = new Trainer("Fearless", "Contender", "trainer@campgladiator.com", "5125125120");
        entityManager.persistAndFlush(alex);

        Optional<Trainer> optFound = trainerRepository.findById(new Long(100));
        assertThat(optFound.isEmpty());
    }

}
