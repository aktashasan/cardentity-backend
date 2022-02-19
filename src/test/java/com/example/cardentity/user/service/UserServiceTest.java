package com.example.cardentity.user.service;

import com.example.cardentity.builder.user.UserBuilder;
import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.model.UserMapperImpl;
import com.example.cardentity.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureDataMongo
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);
        UserDTO findUser = userService.findUserById(savedUser.getId());
        Assertions.assertNotNull(findUser);
    }

    @Test
    void deleteUserById() {
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withFirstName("Hasan")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);
        boolean deletedUser = userService.deleteUserById(savedUser.getId());
        Assertions.assertEquals(deletedUser,Boolean.TRUE);

    }

    @Test
    void findUserByUsernameAndPassword() {
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withUsername("asdfs")
                .withPassword("asdfsad")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);
        UserDTO findUser = userService.findUserByUsernameAndPassword(userDTO.getUsername(),userDTO.getPassword());
        Assertions.assertEquals(savedUser,findUser);
    }

    @Test
    void findByUsername() {
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withUsername("Hasanaktas")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);
        User findUsername = userService.findByUsername(savedUser.getUsername());
        Assertions.assertEquals(savedUser,UserMapperImpl.toDTO(findUsername));

    }
    @Test
    void findAllUsers(){
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .build();
        userService.addUser(userDTO);
        List<UserDTO> userDTOList = userService.findAllUsers();
        Assertions.assertEquals(1,userDTOList.size());
    }
}