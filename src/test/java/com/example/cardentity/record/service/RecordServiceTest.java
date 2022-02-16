package com.example.cardentity.record.service;

import com.example.cardentity.builder.record.RecordBuilder;
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

    @Test
    void addRecord() {
    }

    @Test
    void findRecordById() {
    }

    @Test
    void deleteRecordById() {
    }
}