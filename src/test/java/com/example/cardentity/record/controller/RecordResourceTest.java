package com.example.cardentity.record.controller;

import com.example.cardentity.builder.record.RecordBuilder;
import com.example.cardentity.record.model.Record;
import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.repository.RecordRepository;
import com.example.cardentity.record.service.RecordService;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class RecordResourceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void addRecord() throws Exception {
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();

        String jsonRecord = objectMapper.writeValueAsString(recordDTO);

        ResultActions resultActions = this.mockMvc
                .perform(post("/app/record/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRecord))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        RecordDTO result = objectMapper.readValue(contentAsString,RecordDTO.class);
        Assertions.assertNotNull(result.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deleteRecordById() throws Exception {
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/record/delete/" + savedRecord.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Boolean result = objectMapper.readValue(contentAsString,Boolean.class);
        Assertions.assertEquals(Boolean.TRUE,result);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findRecordById() throws Exception{
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/record/get/id/" + savedRecord.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        RecordDTO result = objectMapper.readValue(contentAsString,RecordDTO.class);
        Assertions.assertEquals(savedRecord.getId(),result.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findRecordByOperationType() throws Exception{
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/record/get/operation/"
                        + savedRecord.getOperationType()))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<RecordDTO> result = objectMapper.readValue(contentAsString,List.class);
        Assertions.assertEquals(1,result.size());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void findByTypeBetween() throws Exception{
        recordRepository.deleteAll();
        RecordDTO recordDTO = new RecordBuilder()
                .buildSomeDummy()
                .build();
        RecordDTO savedRecord = recordService.addRecord(recordDTO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(savedRecord.getTime());
        fromCalendar.add(Calendar.MINUTE, -10);
        Date from = fromCalendar.getTime();
        System.out.println(from);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(savedRecord.getTime());
        toCalendar.add(Calendar.MINUTE, +10);
        Date to = toCalendar.getTime();
        System.out.println(to);

        ResultActions resultActions = this.mockMvc
                .perform(get("/app/record/get/time/"+ from + "/"+ to))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<RecordDTO> result = objectMapper.readValue(contentAsString, List.class);
        Assertions.assertEquals(1,result.size());
    }
}