package com.example.cardentity.record.repository;

import com.example.cardentity.record.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends MongoRepository<Record,String> {
}
