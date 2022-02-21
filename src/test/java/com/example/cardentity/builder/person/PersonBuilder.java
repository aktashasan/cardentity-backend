package com.example.cardentity.builder.person;

import com.example.cardentity.person.model.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PersonBuilder {
    private PersonDTO personDTO = new PersonDTO();

    public PersonBuilder buildSomeDummy(){
        this.personDTO.setCode("20120303");
        this.personDTO.setFirstName("Admin");
        this.personDTO.setLastName("Adminoğlu");
        this.personDTO.setCardId("1234");
        this.personDTO.setAuthority("Manager");
        return this;
    }
    public PersonBuilder withCode(String code){
        this.personDTO.setCode(code);
        return this;
    }
    public PersonBuilder withFirstName(String firstName){
        this.personDTO.setFirstName(firstName);
        return this;
    }
    public PersonBuilder withLastName(String lastName){
        this.personDTO.setLastName(lastName);
        return this;
    }public PersonBuilder withCardId(String cardId){
        this.personDTO.setCardId(cardId);
        return this;
    }
    public PersonDTO build(){ return this.personDTO; }

}
