package com.campgladiator.trainer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TrainerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TrainerControlTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrainerRepository trainerRepository;

    @Test
    public void whenPostTrainer_thenCreateTrainer() throws Exception {
        Trainer alexIn = new Trainer("Fearless", "Contender", "trainer@campgladiator.com", "5125125120");
        Trainer alexOut = new Trainer(new Long(1), "trainer@campgladiator.com", "5125125120", "Fearless", "Contender");
        given(trainerRepository.save(Mockito.any())).willReturn(alexOut);

        mvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(alexIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", is("Fearless")));
        verify(trainerRepository, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(trainerRepository);
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        Trainer alex = new Trainer("Alex", "Smith", "alex.smith@campgladiator.com", "5125125120");
        Trainer john = new Trainer("John", "Doe", "john.doe@campgladiator.com", "5125125120");
        Trainer joe = new Trainer("Joe", "Green", "joe.green@campgladiator.com", "5125125120");
/*
        Employee alex = new Employee("alex");
        Employee john = new Employee("john");
        Employee bob = new Employee("bob");
 */
        List<Trainer> allEmployees = Arrays.asList(alex, john, joe);

        given(trainerRepository.findAll()).willReturn(allEmployees);

        mvc.perform(get("/trainers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].first_name", is(alex.getFirstName())))
                .andExpect(jsonPath("$[1].first_name", is(john.getFirstName())))
                .andExpect(jsonPath("$[2].first_name", is(joe.getFirstName())));
        verify(trainerRepository, VerificationModeFactory.times(1)).findAll();
        reset(trainerRepository);
    }

}
