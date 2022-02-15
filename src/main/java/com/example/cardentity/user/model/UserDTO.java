package com.example.cardentity.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
}
