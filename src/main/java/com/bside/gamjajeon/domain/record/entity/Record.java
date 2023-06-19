package com.bside.gamjajeon.domain.record.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Table(name = "record")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<RecordHashtag> recordHashtags = new ArrayList<>();

    public void addRecordhashtags(RecordHashtag recordHashtag){
        recordHashtags.add(recordHashtag);
        recordHashtag.setRecord(this);
    }

    public static Record createRecord(Long userId, String content, int moodType, Date recordDate){
        Record record = new Record();
        record.setUserId(userId);
        record.setContent(content);
        record.setMoodType(moodType);
        record.setRecordDate(recordDate);
        return record;
    }
    public static Record createRecordWithHashtags(Long userId, String content, int moodType, Date recordDate, List<RecordHashtag> hashtagList){
        Record record = new Record();

        record.setUserId(userId);
        record.setContent(content);
        record.setMoodType(moodType);
        record.setRecordDate(recordDate);
        for(RecordHashtag recordHashtag :hashtagList){
            record.addRecordhashtags(recordHashtag);
        }
        return record;
    }

}

