package com.example.cardentity.builder.record;

import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.record.model.RecordDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class RecordBuilder {

    private RecordDTO recordDTO = new RecordDTO();

    public RecordBuilder buildSomeDummy() throws ParseException {
        this.recordDTO.setOperationType("Mesai");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String currentInString = "31-08-2020 18:20:56";
        Date current = sdf.parse(currentInString);
        recordDTO.setTime(current);
        return this;
    }
    public RecordBuilder withPerson(PersonDTO person){
        this.recordDTO.setPerson(person);
        return this;
    }
    public RecordBuilder withOperationType(String operationType){
        this.recordDTO.setOperationType(operationType);
        return this;
    }
    public RecordDTO build(){ return this.recordDTO;}
}
