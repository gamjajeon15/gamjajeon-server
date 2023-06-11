package com.bside.gamjajeon.domain.user.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "record_hashtag")
@Entity
public class RecordHashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "record_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
