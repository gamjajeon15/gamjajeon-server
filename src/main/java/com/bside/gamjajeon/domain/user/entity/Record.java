package com.bside.gamjajeon.domain.user.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "record")
@Entity
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @Comment("사용자 아이디")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Comment("포스트 내용")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Comment("감정 기록 (0: 감자, 1: 고구마)")
    @Column(name = "mood_type")
    private int moodType;

    @Comment("감정일기 해당 일자")
    @Column(name = "record_date")
    private Date recordDate;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordHashtag> hashtagList = new ArrayList<>();

    @Builder
    public Record(Long userId, String content, int moodType, Date recordDate) {
        this.userId = userId;
        this.content = content;
        this.moodType = moodType;
        this.recordDate = recordDate;
    }
}

