package com.example.cardentity.record.repository;

import com.example.cardentity.record.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordRepository extends MongoRepository<Record,String> {
    List<Record> findByTimeBetween(Date from, Date to);
    List<Record> findByOperationType(String operationType);
}
