package com.example.cardentity.user.service;

import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.model.UserMapperImpl;
import com.example.cardentity.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserDTO addUser(UserDTO userDTO){
        User user = userRepository.save(UserMapperImpl.toEntity(userDTO));
        return UserMapperImpl.toDTO(user);
    }

    public UserDTO findUserById(String id){
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return UserMapperImpl.toDTO(user);
        }
        return null;
    }

    public Boolean deleteUserById(String id){
        userRepository.deleteById(id);
        if (findUserById(id) == null){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public UserDTO findUserByUsernameAndPassword(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username,password);
        log.info(String.valueOf(user));
        return UserMapperImpl.toDTO(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
