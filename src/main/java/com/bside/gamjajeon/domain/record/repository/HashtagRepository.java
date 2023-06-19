package com.bside.gamjajeon.domain.record.repository;

import com.bside.gamjajeon.domain.record.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findByKeyword(String keyword);

}
