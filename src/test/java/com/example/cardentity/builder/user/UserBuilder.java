package com.example.cardentity.builder.user;


import com.example.cardentity.user.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserBuilder {

    private UserDTO userDTO = new UserDTO();

    public UserBuilder buildSomeDummy(){
        this.userDTO.setUsername("admin");
        this.userDTO.setPassword("admin");
        this.userDTO.setFirstName("Admin");
        this.userDTO.setLastName("ADMIN");

        return this;
    }

    public UserBuilder withUsername(String username){
        this.userDTO.setUsername(username);
        return this;
    }

    public UserBuilder withFirstName(String firstName){
        this.userDTO.setFirstName(firstName);
        return this;
    }

    public UserBuilder withLastName(String lastName){
        this.userDTO.setLastName(lastName);
        return this;
    }

    public UserBuilder withPassword(String password){
        this.userDTO.setPassword(password);
        return this;
    }


    public UserDTO build(){
        return this.userDTO;
    }
}
