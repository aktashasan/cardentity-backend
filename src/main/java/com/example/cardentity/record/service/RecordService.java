package com.example.cardentity.record.service;

import com.example.cardentity.record.model.Record;
import com.example.cardentity.record.model.RecordDTO;
import com.example.cardentity.record.model.RecordMapperImpl;
import com.example.cardentity.record.repository.RecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.cardentity.cache.CacheNames.RECORD_BY_ID;
import static com.example.cardentity.cache.CacheNames.RECORD_BY_OPERATION;

@Service
@AllArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Caching(
            put = {
                    @CachePut(cacheNames = RECORD_BY_ID, key = "#result.id", condition = "#result != null")
            },
            evict = {
                    @CacheEvict(cacheNames = RECORD_BY_OPERATION, allEntries = true)
            }
    )
    public RecordDTO addRecord(RecordDTO recordDTO){
        recordDTO.setTime(new Date());
        Record record = recordRepository.save(RecordMapperImpl.toEntity(recordDTO));
        return RecordMapperImpl.toDTO(record);
    }

    @Cacheable(cacheNames = RECORD_BY_ID, key = "#id", unless = "#result == null")
    public RecordDTO findRecordById(String id){
        Optional<Record> optional = recordRepository.findById(id);

        if (optional.isPresent()){
            RecordDTO recordDTO = RecordMapperImpl.toDTO(optional.get());
            return recordDTO;
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = RECORD_BY_ID, key = "#id"),
            @CacheEvict(cacheNames = RECORD_BY_OPERATION, allEntries = true)
    })
    public Boolean deleteRecordById(String id){
        if (!recordRepository.existsById(id)) {
            return Boolean.FALSE;
        }
        recordRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Cacheable(cacheNames = RECORD_BY_OPERATION, key = "#operationType", unless = "#result == null || #result.isEmpty()")
    public List<RecordDTO> findRecordByOperationType(String operationType){
        List<Record> recordList = recordRepository.findByOperationType(operationType);
        return RecordMapperImpl.toDTOList(recordList);
    }


     public List<RecordDTO> findRecordsByTimeBetween(List<Date> from){
        DateRange range = resolveRange(from);
        if (range == null) {
            return Collections.emptyList();
        }
        List<Record> recordList = recordRepository.findByTimeBetween(range.start, range.end);
        return RecordMapperImpl.toDTOList(recordList);
     }

     private DateRange resolveRange(List<Date> rawRange) {
         if (rawRange == null || rawRange.size() < 2) {
             return null;
         }
         Date first = rawRange.get(0);
         Date second = rawRange.get(1);
         if (first == null || second == null) {
             return null;
         }
         Date start = first.before(second) ? first : second;
         Date end = first.before(second) ? second : first;
         return new DateRange(start, end);
     }

     private static final class DateRange {
         private final Date start;
         private final Date end;

         private DateRange(Date start, Date end) {
             this.start = start;
             this.end = end;
         }
     }

}
