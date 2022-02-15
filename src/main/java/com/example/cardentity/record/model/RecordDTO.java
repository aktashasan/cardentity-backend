package com.example.cardentity.record.model;

import com.example.cardentity.person.model.Person;
import com.example.cardentity.person.model.PersonDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordDTO implements Serializable {
    private String id;
    private String operationType;
    private Date time;
    private PersonDTO person;
}
