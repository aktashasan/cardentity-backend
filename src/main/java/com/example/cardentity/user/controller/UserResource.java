package com.example.cardentity.user.controller;

import com.example.cardentity.user.model.User;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class UserResource {
    private UserService userService;

    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping("/user/delete/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping("/user/get/id/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id){
        UserDTO userDTO =userService.findUserById(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/user/get/usernameAndPassword/{username}/{password}")
    public ResponseEntity<UserDTO> findUserByUsernameAndPassword(@PathVariable String username,
                                                                 @PathVariable String password){
        return ResponseEntity.ok(userService.findUserByUsernameAndPassword(username,password));
    }

    @GetMapping("/user/get/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }
    @GetMapping("/users/get")
    public ResponseEntity<List<UserDTO>> findAllUsers(){

        return ResponseEntity.ok(userService.findAllUsers());
    }
}
