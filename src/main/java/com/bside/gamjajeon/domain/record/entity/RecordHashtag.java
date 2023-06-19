package com.bside.gamjajeon.domain.record.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "record_hashtag")
@Entity
@Setter
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

    public static RecordHashtag createRecordHashtag(Hashtag hashtag){
        RecordHashtag recordHashtag = new RecordHashtag();
        recordHashtag.setHashtag(hashtag);
        return recordHashtag;
    }
}
