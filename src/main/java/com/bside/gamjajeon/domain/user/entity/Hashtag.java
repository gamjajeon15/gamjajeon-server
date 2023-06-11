package com.bside.gamjajeon.domain.user.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hashtag")
@Entity
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Comment("해시태그 내용")
    private String keyword;

    @Builder
    protected Hashtag(String keyword) {
        this.keyword = keyword;
    }
}
