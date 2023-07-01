package com.bside.gamjajeon.domain.record.repository;

import com.bside.gamjajeon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.gamjajeon.domain.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    void deleteByUser(User user);
}
