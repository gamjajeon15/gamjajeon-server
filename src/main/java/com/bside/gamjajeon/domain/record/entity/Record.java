package com.bside.gamjajeon.domain.record.entity;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.user.entity.User;
import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "record", cascade = CascadeType.REMOVE)
    private Image image;

    @Comment("포스트 내용")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Comment("감정 기록 (0: 감자, 1: 고구마)")
    @Column(name = "mood_type")
    private int moodType;

    @Comment("감정일기 해당 일자")
    @Column(name = "record_date")
    private LocalDate recordDate;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<RecordHashtag> recordHashtags = new ArrayList<>();

    public void addRecordhashtags(RecordHashtag recordHashtag) {
        recordHashtags.add(recordHashtag);
        recordHashtag.setRecord(this);
    }

    @Builder
    public Record(User user, RecordRequest recordRequest) {
        this.id = recordRequest.getId();
        this.user = user;
        this.content = recordRequest.getContent();
        this.moodType = recordRequest.getMoodType();
        this.recordDate = recordRequest.getRecordDate();
    }

    @Builder
    public Record(User user, RecordRequest recordRequest, List<RecordHashtag> recordHashtags) {
        this(user, recordRequest);
        for (RecordHashtag recordHashtag : recordHashtags) {
            this.addRecordhashtags(recordHashtag);
        }
    }

}

