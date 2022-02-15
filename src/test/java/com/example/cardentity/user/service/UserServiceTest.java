package com.example.cardentity.user.service;

import com.example.cardentity.builder.user.UserBuilder;
import com.example.cardentity.user.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureDataMongo
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void addUser() {
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    void findUserById() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void findUserByUsernameAndPassword() {
    }

    @Test
    void findByUsername() {
    }
}