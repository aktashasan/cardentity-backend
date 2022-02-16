package com.example.cardentity.record.service;

import com.example.cardentity.builder.person.PersonBuilder;
import com.example.cardentity.builder.record.RecordBuilder;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.person.service.PersonService;
import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.repository.RecordRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureDataMongo
class RecordServiceTest {
    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private PersonService personService;

    @Test
    void addRecord() {
        PersonDTO personDTO = new PersonBuilder()
                .buildSomeDummy()
                .build();
        PersonDTO savedPerson = personService.addPerson(personDTO);
        System.out.println(savedPerson);

        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .withOperationType("jhsgdh")
                .withPerson(savedPerson)
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        Assertions.assertNotNull(savedRecord.getId());

    }

    @Test
    void findRecordById() {
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .withOperationType("askdh")
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        RecordDTO findRecord = recordService.findRecordById(savedRecord.getId());
        Assertions.assertNotNull(findRecord);
    }

    @Test
    void deleteRecordById() {
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .withOperationType("sdlfjshd")
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        Boolean deletedRecord = recordService.deleteRecordById(savedRecord.getId());
        Assertions.assertEquals(deletedRecord,true);
    }
}