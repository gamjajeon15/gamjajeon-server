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
import com.bside.gamjajeon.domain.user.entity.User;

public interface RecordRepository extends JpaRepository<Record, Long> {
	@Query("from Record r where r.user =:user and year(r.recordDate) =:year and month(r.recordDate) =:month")
	List<Record> findAllbyRecordDate(@Param("user") User user, @Param("year") int year, @Param("month") int month,
		Sort sort);
	void deleteByUser(User user);
}
