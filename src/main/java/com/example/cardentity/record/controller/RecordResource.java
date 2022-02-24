package com.example.cardentity.record.controller;

import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class RecordResource {

    private RecordService recordService;

    @PostMapping("/record/save")
    public ResponseEntity<RecordDTO> addRecord(@RequestBody RecordDTO recordDTO){
        return ResponseEntity.ok(recordService.addRecord(recordDTO));
    }

    @GetMapping("/record/delete/{id}")
    public ResponseEntity<Boolean> deleteRecordById(@PathVariable String id){
        return ResponseEntity.ok(recordService.deleteRecordById(id));
    }

    @GetMapping("/record/get/id/{id}")
    public ResponseEntity<RecordDTO> findRecordById(@PathVariable("id") String id){
        RecordDTO recordDTO = recordService.findRecordById(id);
        return recordDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(recordDTO);
    }

    @GetMapping("/record/get/operation/{operationType}")
    public ResponseEntity<List<RecordDTO>> findRecordByOperationType(@PathVariable String operationType){
        List<RecordDTO> recordDTOS = recordService.findRecordByOperationType(operationType);
        return recordDTOS.size() > 0 ? ResponseEntity.ok(recordDTOS) : ResponseEntity.notFound().build();
    }

    @PostMapping("/records/get/timeBetween")
    public ResponseEntity<List<RecordDTO>> findRecordsByTypeBetween(@RequestBody List<Date> dates){
        List<RecordDTO> recordDTOS = recordService.findRecordsByTimeBetween(dates);
        return recordDTOS.size() > 0 ? ResponseEntity.ok(recordDTOS) : ResponseEntity.notFound().build();
    }
}
