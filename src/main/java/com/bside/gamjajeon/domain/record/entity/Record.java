package com.bside.gamjajeon.domain.record.entity;

import static javax.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.bside.gamjajeon.domain.user.entity.User;
import com.bside.gamjajeon.global.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public void addRecordhashtags(RecordHashtag recordHashtag) {
		recordHashtags.add(recordHashtag);
		recordHashtag.setRecord(this);
	}

	public static Record createRecord(User user, String content, int moodType, Date recordDate) {
		Record record = new Record();
		record.setUser(user);
		record.setContent(content);
		record.setMoodType(moodType);
		record.setRecordDate(recordDate);
		return record;
	}

	public static Record createRecordWithHashtags(User user, String content, int moodType, Date recordDate,
		List<RecordHashtag> hashtagList) {
		Record record = new Record();

		record.setUser(user);
		record.setContent(content);
		record.setMoodType(moodType);
		record.setRecordDate(recordDate);
		for (RecordHashtag recordHashtag : hashtagList) {
			record.addRecordhashtags(recordHashtag);
		}
		return record;
	}

}

