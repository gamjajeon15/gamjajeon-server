package com.bside.gamjajeon.domain.record.entity;

import com.bside.gamjajeon.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "record_id")
    private Record record;

    @Comment("storage url")
    private String url;

    @Comment("이미지 가로 길이")
    private int width;

    @Comment("이미지 세로 길이")
    private int height;

    public Image(int width, int height, String url, Record record) {
        this.record = record;
        this.url = url;
        this.width = width;
        this.height = height;
    }
}
