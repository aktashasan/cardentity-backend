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
import java.util.Calendar;
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
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        System.out.println(formatter.format(savedRecord.getTime()));

        Calendar from = Calendar.getInstance();
        from.setTime(savedRecord.getTime());
        from.add(Calendar.MINUTE, -10);
        System.out.println(from.getTime());

        Calendar to = Calendar.getInstance();
        to.setTime(savedRecord.getTime());
        to.add(Calendar.MINUTE, +10);
        System.out.println(to.getTime());

        List<RecordDTO> recordDTOList = recordService.findByTimeBetween(from.getTime(),to.getTime());
        Assertions.assertEquals(1,recordDTOList.size());
        System.out.println(recordDTOList);
    }
}