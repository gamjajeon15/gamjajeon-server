package com.bside.gamjajeon.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.gamjajeon.domain.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
