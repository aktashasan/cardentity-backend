package com.example.cardentity.record.service;

import com.example.cardentity.record.repository.RecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

}
