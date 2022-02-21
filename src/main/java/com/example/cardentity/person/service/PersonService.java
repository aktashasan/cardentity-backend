package com.example.cardentity.person.service;

import com.example.cardentity.person.model.Person;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.model.PersonMapperImpl;
import com.example.cardentity.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDTO addPerson(PersonDTO personDTO){
        Person person = personRepository.save(PersonMapperImpl.toEntity(personDTO));
        return PersonMapperImpl.toDTO(person);
    }

    public PersonDTO findPersonById(String id){
        Optional<Person> optional = personRepository.findById(id);

        if (optional.isPresent()){
            Person person = optional.get();
            return PersonMapperImpl.toDTO(person);
        }
        return null;
    }

    public Boolean deletePersonById(String id){
        personRepository.deleteById(id);
        if (findPersonById(id) == null){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public List<PersonDTO> findAllPersons(){
        List<Person> personList = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person: personList){
            personDTOList.add(PersonMapperImpl.toDTO(person));
        }

        return personDTOList;
    }

    public PersonDTO findPersonByCode(String code){
        Person person = personRepository.findByCode(code);
        return PersonMapperImpl.toDTO(person);
    }

}
