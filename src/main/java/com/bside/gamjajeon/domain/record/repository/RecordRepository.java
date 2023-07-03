package com.bside.gamjajeon.domain.record.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bytebuddy.asm.Advice;

import com.bside.gamjajeon.domain.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
	@Query("from Record r where year(r.recordDate) =:year and month(r.recordDate) =:month")
	List<Record> findAllbyRecordDate(@Param("year") int year, @Param("month") int month, Sort sort);
}
