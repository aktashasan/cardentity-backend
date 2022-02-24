package com.example.cardentity.record.controller;

import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.service.RecordService;
import lombok.AllArgsConstructor;
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
        if (recordDTO == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recordDTO);
    }

    @GetMapping("/record/get/operation/{operationType}")
    public ResponseEntity<List<RecordDTO>> findRecordByOperationType(@PathVariable String operationType){
        return ResponseEntity.ok(recordService.findRecordByOperationType(operationType));
    }

    @PostMapping("/records/get/timeBetween")
    public ResponseEntity<List<RecordDTO>> findByTypeBetween(@RequestBody List<Date> dates){
        return ResponseEntity.ok(recordService.findRecordsByTimeBetween(dates));
    }
}
