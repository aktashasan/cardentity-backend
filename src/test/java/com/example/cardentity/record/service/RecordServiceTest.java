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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    void addRecord() throws ParseException {
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
    void deleteRecordById() throws ParseException {
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .withOperationType("sdlfjshd")
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        Boolean record = recordService.deleteRecordById(savedRecord.getId());
        Assertions.assertEquals(Boolean.TRUE,record);
    }

    @Test
    void findRecordByOperationType() throws ParseException {
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .withOperationType("Giriş")
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        recordDTO.setOperationType("çıkış");
        RecordDTO savedRecord2 = recordService.addRecord(recordDTO);

        List<RecordDTO> recordDTOList = recordService.findRecordByOperationType(savedRecord2.getOperationType());
        System.out.println(recordDTOList);
        Assertions.assertEquals(1,recordDTOList.size());
    }

    @Test
    void findByTimeBetween() throws Exception {
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        String fromDateInString = "31-08-2020 10:20:56";
        Date from = sdf.parse(fromDateInString);
        String toDateInString = "31-08-2021 10:21:56";
        Date to = sdf.parse(toDateInString);
        List<RecordDTO> recordDTOList = recordService.findByTimeBetween(from,to);

        Assertions.assertEquals(1,recordDTOList.size());



    }
}