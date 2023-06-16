package com.bside.gamjajeon.domain.user.repository;

import com.bside.gamjajeon.domain.user.entity.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HashtagRepository {
    private final EntityManager em;

    public List<Hashtag> findHashtagByKeyword(String keyword){
       return em.createQuery("select h from Hashtag h where h.keyword = :keyword", Hashtag.class)
               .setParameter("keyword", keyword)
               .getResultList();
    }

    public Hashtag createHashtag(String keyword){
        Hashtag hashtag = Hashtag.builder().keyword(keyword).build();
        em.persist(hashtag);
        return hashtag;
    }
}
