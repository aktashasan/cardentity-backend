package com.example.cardentity.person.service;

import com.example.cardentity.builder.person.PersonBuilder;
import com.example.cardentity.person.model.Person;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.repository.PersonRepository;
import com.example.cardentity.user.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureDataMongo
class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void addPerson() {
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        PersonDTO savedUser = personService.addPerson(personDTO);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    void findPersonById() {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        PersonDTO savedUser = personService.addPerson(personDTO);
        PersonDTO findUser = personService.findPersonById(savedUser.getId());
        Assertions.assertNotNull(findUser);
    }

    @Test
    void deletePersonById() {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        PersonDTO savedUser = personService.addPerson(personDTO);
        Boolean deletedUser = personService.deletePersonById(savedUser.getId());
        Assertions.assertEquals(deletedUser,true);
    }

    @Test
    void findAllPersons() {
        personRepository.deleteAll();
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();
        personService.addPerson(personDTO);
        List<PersonDTO> personDTOList = personService.findAllPersons();
        Assertions.assertEquals(1,personDTOList.size());

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