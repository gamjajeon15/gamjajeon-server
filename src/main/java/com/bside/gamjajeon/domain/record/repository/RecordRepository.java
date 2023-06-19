package com.bside.gamjajeon.domain.record.repository;

import com.bside.gamjajeon.domain.record.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
