package com.example.cardentity.user.controller;

import com.example.cardentity.builder.user.UserBuilder;
import com.example.cardentity.person.model.PersonDTO;
import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.user.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class UserResourceTest {

    private final ObjectMapper objectMapper= new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void addUser() throws Exception{
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .build();

        String jsonUser = objectMapper.writeValueAsString(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(post("/app/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUser))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        UserDTO result = objectMapper.readValue(contentAsString, UserDTO.class);
        Assertions.assertNotNull(result.getId());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteUserById() throws Exception{
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/user/delete/" + savedUser.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Boolean result = objectMapper.readValue(contentAsString, Boolean.class);
        Assertions.assertEquals(Boolean.TRUE,result);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findUserById() throws Exception {
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/user/get/id/" + savedUser.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        UserDTO result = objectMapper.readValue(contentAsString, UserDTO.class);
        Assertions.assertEquals(savedUser.getId(), result.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findUserByUsernameAndPassword() throws Exception{
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withUsername("hasan")
                .withPassword("hasan")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/user/get/usernameAndPassword/"
                        + savedUser.getUsername() + "/"
                        + savedUser.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        UserDTO result = objectMapper.readValue(contentAsString, UserDTO.class);
        Assertions.assertEquals(savedUser.getUsername(),result.getUsername());
        Assertions.assertEquals(savedUser.getPassword(),result.getPassword());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findByUsername() throws Exception {
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .withUsername("hasan")
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/user/get/username/" + userDTO.getUsername()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        UserDTO result = objectMapper.readValue(contentAsString, UserDTO.class);
        Assertions.assertEquals(savedUser.getUsername(),result.getUsername());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findAllUsers() throws Exception{
        userRepository.deleteAll();
        UserDTO userDTO = new UserBuilder()
                .buildSomeDummy()
                .build();
        UserDTO savedUser = userService.addUser(userDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/users/get/"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<UserDTO> result = objectMapper.readValue(contentAsString, List.class);
        Assertions.assertEquals(1,result.size());
    }
}