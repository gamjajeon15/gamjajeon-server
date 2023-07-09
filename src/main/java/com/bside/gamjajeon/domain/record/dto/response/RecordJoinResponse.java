package com.bside.gamjajeon.domain.record.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bside.gamjajeon.domain.record.entity.Record;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;

@Data
public class RecordJoinResponse {
	private Long recordId;

	private String content = "";

	private int moodType;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate recordDate;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updatedAt;

	private List<String> hashtagList = new ArrayList<String>();
	private String imageUrl = "";

	public RecordJoinResponse(Record record) {
		this.recordId = record.getId();
		this.moodType = record.getMoodType();
		this.recordDate = record.getRecordDate();
		this.createdAt = record.getCreatedAt();
		this.updatedAt = record.getUpdatedAt();

		if (record.getContent() != null) {
			this.content = record.getContent();
		}

		if (record.getRecordHashtags() != null) {
			this.hashtagList = record.getRecordHashtags().stream()
				.map(recordHashtag -> recordHashtag.getHashtag().getKeyword())
				.collect(Collectors.toList());
		}

		if (record.getImage() != null) {
			this.imageUrl = record.getImage().getUrl();
		}
	}
}
