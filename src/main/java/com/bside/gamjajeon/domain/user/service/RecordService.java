package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.user.entity.Hashtag;
import com.bside.gamjajeon.domain.user.entity.Record;
import com.bside.gamjajeon.domain.user.entity.RecordHashtag;
import com.bside.gamjajeon.domain.user.repository.HashtagRepository;
import com.bside.gamjajeon.domain.user.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long save(RecordRequest recordRequest) {

        // Hashtag 존재 여부 validation
        if(!ObjectUtils.isEmpty(recordRequest.getHashtagList())){
            List<Hashtag> hashtagList = hashtagValidationRecord(recordRequest);

            // Record-Hashtag 생성
            List<RecordHashtag> recordHashtags = new ArrayList<RecordHashtag>();

            for (Hashtag hashtag : hashtagList) {
                RecordHashtag recordHashtag = RecordHashtag.createRecordHashtag(hashtag);
                recordHashtags.add(recordHashtag);
            }

            // Record 저장
            Record record = Record.createRecordWithHashtags(recordRequest.getUserId(), recordRequest.getContent(), recordRequest.getMoodType(), recordRequest.getRecordDate(), recordHashtags);
            recordRepository.save(record);
            return record.getId();
        }else{
            Record record = Record.createRecord(recordRequest.getUserId(), recordRequest.getContent(), recordRequest.getMoodType(), recordRequest.getRecordDate());
            recordRepository.save(record);
            return record.getId();
        }

    }
    @Transactional
    public List<Hashtag> hashtagValidationRecord(RecordRequest recordRequest){

        List<Hashtag> hashtaglist = new ArrayList<Hashtag>();

        for(String keyword : recordRequest.getHashtagList()){
            List<Hashtag> findHashtag = hashtagRepository.findHashtagByKeyword(keyword);
            if(!findHashtag.isEmpty()){
                hashtaglist.add(findHashtag.get(0));
            }else{
                Hashtag savedHashtag = hashtagRepository.createHashtag(keyword);
                hashtaglist.add(savedHashtag);
            }
        }
        return hashtaglist;
    }

}
