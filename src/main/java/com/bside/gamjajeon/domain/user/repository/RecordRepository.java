package com.bside.gamjajeon.domain.user.repository;

import com.bside.gamjajeon.domain.user.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;
    private final HashtagRepository hashtagRepository;

    //save record
    public Long save(Record record) {
        em.persist(record);
        return record.getId();
    }

    // update record

    // delete record

    // select one record
    public Record findOne(Long id){
        return em.find(Record.class, id);
    }

    // select all records

}
