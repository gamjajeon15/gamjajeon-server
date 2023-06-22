package com.bside.gamjajeon.domain.record.entity;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bside.gamjajeon.global.common.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public static RecordHashtag createRecordHashtag(Hashtag hashtag) {
		RecordHashtag recordHashtag = new RecordHashtag();
		recordHashtag.setHashtag(hashtag);
		return recordHashtag;
	}
}
