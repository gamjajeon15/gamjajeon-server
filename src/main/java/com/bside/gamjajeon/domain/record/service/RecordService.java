package com.bside.gamjajeon.domain.record.service;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.dto.response.RecordResponse;
import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.record.entity.RecordHashtag;
import com.bside.gamjajeon.domain.record.repository.HashtagRepository;
import com.bside.gamjajeon.domain.record.repository.RecordRepository;
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
    public RecordResponse save(RecordRequest recordRequest) {

        if(!ObjectUtils.isEmpty(recordRequest.getHashtagList())){
            List<Hashtag> hashtagList = validateHashtags(recordRequest);
            return createRecordWithHashtags(recordRequest, hashtagList);
        }else{
            return createRecordWithoutHashtags(recordRequest);
        }

    }
    @Transactional
    public List<Hashtag> validateHashtags(RecordRequest recordRequest){

        List<Hashtag> hashtaglist = new ArrayList<Hashtag>();

        for(String keyword : recordRequest.getHashtagList()){
            List<Hashtag> findHashtag = hashtagRepository.findByKeyword(keyword);
            if(!findHashtag.isEmpty()){
                hashtaglist.add(findHashtag.get(0));
            }else{
                Hashtag savedHashtag = hashtagRepository.save(new Hashtag(keyword));
                hashtaglist.add(savedHashtag);
            }
        }
        return hashtaglist;
    }

    @Transactional
    public RecordResponse createRecordWithoutHashtags(RecordRequest recordRequest){
        Record record = Record.createRecord(recordRequest.getUserId(), recordRequest.getContent(), recordRequest.getMoodType(), recordRequest.getRecordDate());
        recordRepository.save(record);
        return new RecordResponse(record.getId());
    }

    @Transactional
    public RecordResponse createRecordWithHashtags(RecordRequest recordRequest, List<Hashtag> hashtagList){
        List<Hashtag> convertedHashtags = validateHashtags(recordRequest);
        List<RecordHashtag> recordHashtags = new ArrayList<RecordHashtag>();

        for (Hashtag hashtag : convertedHashtags) {
            RecordHashtag recordHashtag = RecordHashtag.createRecordHashtag(hashtag);
            recordHashtags.add(recordHashtag);
        }

        Record record = Record.createRecordWithHashtags(recordRequest.getUserId(), recordRequest.getContent(), recordRequest.getMoodType(), recordRequest.getRecordDate(), recordHashtags);
        recordRepository.save(record);
        return new RecordResponse(record.getId());
    }

}
