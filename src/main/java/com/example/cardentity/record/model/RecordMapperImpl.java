package com.example.cardentity.record.model;

import com.example.cardentity.person.model.PersonMapperImpl;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RecordMapperImpl implements Serializable {

    public static RecordDTO toDTO(Record record){
        if (record == null) {
            return null;
        }

        RecordDTO recordDTO = new RecordDTO();

        recordDTO.setId(record.getId());
        recordDTO.setOperationType(record.getOperationType());
        recordDTO.setTime(record.getTime());
        recordDTO.setPerson(PersonMapperImpl.toDTO(record.getPerson()));

        return recordDTO;
    }

    public static Record toEntity(RecordDTO recordDTO){
        if (recordDTO == null) {
            return null;
        }

        Record record = new Record();

        record.setId(recordDTO.getId());
        record.setOperationType(recordDTO.getOperationType());
        record.setTime(recordDTO.getTime());
        record.setPerson(PersonMapperImpl.toEntity(recordDTO.getPerson()));

        return record;
    }
}