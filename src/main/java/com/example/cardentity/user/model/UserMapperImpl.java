package com.example.cardentity.user.model;

import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl {

    public static User toEntity (UserDTO userDTO){
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }

    public static UserDTO toDTO(User user){
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
