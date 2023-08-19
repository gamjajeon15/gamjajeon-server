package com.bside.gamjajeon.domain.record.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.user.entity.User;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	Optional<Hashtag> findByKeyword(String keyword);

	@Query(value = "select distinct sub.keyword from "
		+ "(select h.keyword from hashtag h inner join record_hashtag rh on rh.hashtag_id = h.hashtag_id " +
		"inner join record r on rh.record_id = r.record_id where r.user_id = :userId order by r.record_id desc) as sub"
		+ " limit 10", nativeQuery = true)
	List<String> findAllKeywordByUser(Long userId);

	@Query("select h.keyword, r.moodType from Hashtag h join fetch RecordHashtag rh on rh.hashtag = h " +
		"join fetch Record r on r = rh.record "
		+ "where r.user = :user and r.recordDate >= DATE(:startDate) and r.recordDate <= DATE(:endDate) order by r.recordDate desc")
	List<List<String>> findWeeklyKeywordByUserAndDate(User user, LocalDate startDate, LocalDate endDate);
}
