package com.example.cardentity.person.controller;

import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class PersonResource {

    private PersonService personService;

    @PostMapping("/person/save")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO){
        PersonDTO savedPerson = personService.addPerson(personDTO);
        if (savedPerson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedPerson);
    }

    @GetMapping("/person/delete/{id}")
    public ResponseEntity<Boolean> deletePersonById(@PathVariable String id){
        return ResponseEntity.ok(personService.deletePersonById(id));
    }

    @GetMapping("/person/get/id/{id}")
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable String id){
        PersonDTO personDTO = personService.findPersonById(id);
        if (personDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personDTO);
    }

    @GetMapping("/person/get/code/{code}")
    public ResponseEntity<PersonDTO> findPersonByCode(@PathVariable String code){
        PersonDTO personDTO = personService.findPersonByCode(code);
        if (personDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personDTO);
    }

    @GetMapping("/persons/get")
    public ResponseEntity<List<PersonDTO>> findAllPersons(){
        return ResponseEntity.ok(personService.findAllPersons());
    }



}
