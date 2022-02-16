package com.example.cardentity.person.service;

import com.example.cardentity.person.model.Person;
import com.example.cardentity.person.model.PersonDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureDataMongo
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    void addPerson() {
    }

    @Test
    void findPersonById() {
    }

    @Test
    void deletePersonById() {
    }

    @Test
    void findAllPersons() {
    }

    @Test
    void findPersonByIdentityNumber() {
        PersonDTO personDTO = new PersonDTO();

        personDTO.setIdentityNumber("12312347");

        PersonDTO savedPerson = personService.addPerson(personDTO);
        PersonDTO resultPerson= personService.findPersonByIdentityNumber(savedPerson.getIdentityNumber());
        Assertions.assertEquals(savedPerson,resultPerson);
    }
}