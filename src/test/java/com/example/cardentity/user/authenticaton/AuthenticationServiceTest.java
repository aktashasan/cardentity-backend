package com.example.cardentity.user.authenticaton;

import com.example.cardentity.builder.user.UserBuilder;
import com.example.cardentity.user.authentication.AuthenticationService;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.repository.UserRepository;
import com.example.cardentity.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addAuthenticate(){
        userRepository.deleteAll();

        UserDTO userDTO = new UserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(savedUser.getUsername(),savedUser.getPassword());

        Assertions.assertNotNull(this.authenticationService.authenticate(token));

    }

}


