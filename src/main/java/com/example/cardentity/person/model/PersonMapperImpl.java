package com.example.cardentity.person.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PersonMapperImpl implements Serializable {

    public static PersonDTO toDTO(Person person){
        if (person == null) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId(person.getId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setIdentityNumber(person.getIdentityNumber());
        personDTO.setAuthority(person.getAuthority());
        personDTO.setCardId(person.getCardId());

        return personDTO;
    }

    public static Person toEntity(PersonDTO personDTO){
        if (personDTO == null) {
            return null;
        }

        Person person = new Person();

        person.setId(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setIdentityNumber(personDTO.getIdentityNumber());
        person.setAuthority(personDTO.getAuthority());
        person.setCardId(personDTO.getCardId());

        return person;
    }
}
