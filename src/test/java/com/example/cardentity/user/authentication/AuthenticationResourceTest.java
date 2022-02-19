package com.example.cardentity.user.authentication;

import com.example.cardentity.builder.user.UserBuilder;
import com.example.cardentity.user.model.UserDTO;
import com.example.cardentity.user.repository.UserRepository;
import com.example.cardentity.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
class AuthenticationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void login() throws Exception{
        userRepository.deleteAll();

        UserDTO userDTO = new UserBuilder()
                .withUsername("admin")
                .withPassword("admin")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(
                        get("/app/login" ))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        UserDTO result = objectMapper.readValue(contentAsString,UserDTO.class);
        Assertions.assertEquals(savedUser,result);
    }

}