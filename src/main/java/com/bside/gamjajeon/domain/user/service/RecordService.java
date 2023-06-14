package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.user.entity.Record;
import com.bside.gamjajeon.domain.user.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Transactional
    public void save(RecordRequest recordRequest) {

        Record newRecord = Record.builder()
                .userId(recordRequest.getUserId())
                .content(recordRequest.getContent())
                .moodType(recordRequest.getMoodType())
                .recordDate(recordRequest.getRecordDate())
                .build();

        Long recordId = recordRepository.save(newRecord);

        // 해시 태그 validation


    }
    
}
