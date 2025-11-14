package com.example.cardentity.record.model;

import com.example.cardentity.person.model.Person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document
public class Record implements Serializable {

    @Id
    private String id;

    @Field
    @Indexed
    private String operationType;

    @Field
    @Indexed
    private Date time;

    @DBRef
    @Field
    private Person person;
}

