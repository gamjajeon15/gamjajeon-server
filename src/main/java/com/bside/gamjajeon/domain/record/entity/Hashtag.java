package com.bside.gamjajeon.domain.record.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.bside.gamjajeon.global.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@Column(nullable = false, unique = true)
	private String keyword;

	@Builder
	public Hashtag(String keyword) {
		this.keyword = keyword;
	}


}
