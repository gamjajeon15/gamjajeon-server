package com.bside.gamjajeon.domain.record.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.gamjajeon.domain.record.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	Optional<Hashtag> findByKeyword(String keyword);

}
