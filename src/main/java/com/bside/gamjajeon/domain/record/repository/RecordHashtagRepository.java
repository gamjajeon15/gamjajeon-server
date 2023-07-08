package com.bside.gamjajeon.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.record.entity.RecordHashtag;

public interface RecordHashtagRepository extends JpaRepository<RecordHashtag, Long> {
}
