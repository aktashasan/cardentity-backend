package com.example.cardentity.builder.person;

import com.example.cardentity.person.model.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PersonBuilder {
    private PersonDTO personDTO = new PersonDTO();

    public PersonBuilder buildSomeDummy(){
        this.personDTO.setIdentityNumber("1234");
        this.personDTO.setFirstName("admin");
        this.personDTO.setLastName("admin");
        this.personDTO.setCardId("1234");
        return this;
    }
    public PersonBuilder withIdentityNumber(String identityNumber){
        this.personDTO.setIdentityNumber(identityNumber);
        return this;
    }
    public PersonBuilder withFirstName(String firstName){
        this.personDTO.setFirstName(firstName);
        return this;
    }
    public PersonBuilder withLastName(String lastName){
        this.personDTO.setIdentityNumber(lastName);
        return this;
    }public PersonBuilder withCardId(String cardId){
        this.personDTO.setIdentityNumber(cardId);
        return this;
    }
    public PersonDTO build(){ return this.personDTO; }

}
