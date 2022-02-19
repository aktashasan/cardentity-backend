package com.example.cardentity.record.service;

import com.example.cardentity.record.model.Record;
import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.model.RecordMapperImpl;
import com.example.cardentity.record.repository.RecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordDTO addRecord(RecordDTO recordDTO){
        recordDTO.setTime(new Date());
        Record record = recordRepository.save(RecordMapperImpl.toEntity(recordDTO));
        return RecordMapperImpl.toDTO(record);
    }

    public RecordDTO findRecordById(String id){
        Optional<Record> optional = recordRepository.findById(id);

        if (optional.isPresent()){
            RecordDTO recordDTO = RecordMapperImpl.toDTO(optional.get());
            return recordDTO;
        }
        return null;
    }

    public Boolean deleteRecordById(String id){
        recordRepository.deleteById(id);
        if (findRecordById(id) == null){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public List<RecordDTO> findRecordByOperationType(String operationType){
        List<Record> recordList = recordRepository.findByOperationType(operationType);
        return RecordMapperImpl.toDTOList(recordList);
    }


     public List<RecordDTO> findByTimeBetween(Date time, Date to){
        List<Record> recordList = recordRepository.findByTimeBetween(time, to);
        return RecordMapperImpl.toDTOList(recordList);
     }

}
