package com.example.cardentity.person.controller;

import com.example.cardentity.builder.person.PersonBuilder;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.repository.PersonRepository;
import com.example.cardentity.person.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class PersonResourceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void addPerson() throws Exception {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();

        String jsonPerson = objectMapper.writeValueAsString(personDTO);

        ResultActions resultActions = this.mockMvc
                .perform(post("/app/person/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        PersonDTO result = objectMapper.readValue(contentAsString, PersonDTO.class);
        Assertions.assertNotNull(result.getId());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deletePersonById() throws Exception {
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();
        PersonDTO savedPerson = personService.addPerson(personDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/person/delete/" + savedPerson.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Boolean result = objectMapper.readValue(contentAsString, Boolean.class);
        Assertions.assertEquals(Boolean.TRUE,result);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findPersonById() throws Exception {
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();
        PersonDTO savedPerson = personService.addPerson(personDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/person/get/id/" + savedPerson.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        PersonDTO result = objectMapper.readValue(contentAsString, PersonDTO.class);
        Assertions.assertEquals(savedPerson.getId(), result.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findPersonByCode() throws Exception {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .withCode("A2372")
                .build();
        PersonDTO savedPerson = personService.addPerson(personDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/person/get/code/" + savedPerson.getCode()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        PersonDTO result = objectMapper.readValue(contentAsString, PersonDTO.class);
        Assertions.assertEquals(savedPerson.getId(), result.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findAllPersons() throws Exception {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();
        PersonDTO savedPerson = personService.addPerson(personDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/persons/get/"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<PersonDTO> result = objectMapper.readValue(contentAsString,List.class);
        Assertions.assertEquals(1,result.size());
    }
}