package com.bside.gamjajeon.domain.record.repository;

import com.bside.gamjajeon.domain.record.entity.Hashtag;
import com.bside.gamjajeon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByKeyword(String keyword);

    @Query("select distinct h.keyword from Hashtag h join fetch RecordHashtag rh on rh.hashtag = h " +
            "join fetch Record r on r = rh.record where r.user = :user")
    List<String> findAllKeywordByUser(User user);
}
