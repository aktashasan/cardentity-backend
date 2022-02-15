package com.example.cardentity.person.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDTO implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String authority;
    private String identityNumber;
    private String cardId;
}
