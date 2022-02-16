package com.example.cardentity.builder.record;

import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.record.model.RecordDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RecordBuilder {

    private RecordDTO recordDTO = new RecordDTO();
    private PersonDTO personDTO = new PersonDTO();

    public RecordBuilder buildSomeDummy(){
        this.recordDTO.setPerson(personDTO);
        this.recordDTO.setOperationType("Mesai");

        return this;
    }
    public RecordBuilder withPerson(PersonDTO person){
        this.recordDTO.setPerson(personDTO);
        return this;
    }
    public RecordBuilder withOperationType(String operationType){
        this.recordDTO.setOperationType(operationType);
        return this;
    }
    public RecordDTO build(){ return this.recordDTO;}
}
