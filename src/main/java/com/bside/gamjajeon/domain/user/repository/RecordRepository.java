package com.bside.gamjajeon.domain.user.repository;

import com.bside.gamjajeon.domain.user.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    //save record
    public Long save(Record record) {
        em.persist(record);
        return record.getId();
    }

    // update record

    // delete record

    // select one record

    // select all records

}
