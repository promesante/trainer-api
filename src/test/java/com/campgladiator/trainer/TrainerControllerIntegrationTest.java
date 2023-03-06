package com.campgladiator.trainer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TrainerApiApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TrainerRepository trainerRepository;

    @AfterEach
    public void resetDb() {
        trainerRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateTrainer() throws IOException, Exception {
        Trainer alexIn = new Trainer("Alex", "Smith", "trainer@campgladiator.com", "5125125120");
        mvc.perform(post("/trainers").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alexIn)));

        Iterable<Trainer> found = trainerRepository.findAll();
        assertThat(found).extracting(Trainer::getFirstName).containsOnly("Alex");
    }

    @Test
    public void givenTrainers_whenGetTrainers_thenStatus200() throws Exception {
        createTestTrainer("Alex", "Smith", "alex.smith@campgladiator.com", "5125125120");
        createTestTrainer("John", "Doe", "john.doe@campgladiator.com", "5125125120");

        mvc.perform(get("/trainers").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].first_name", is("Alex")))
                .andExpect(jsonPath("$[1].first_name", is("John")));
    }

    private void createTestTrainer(String firstName, String lastName, String email, String phone) {
        Trainer trainer = new Trainer(firstName, lastName, email, phone);
        trainerRepository.save(trainer);
    }

}
