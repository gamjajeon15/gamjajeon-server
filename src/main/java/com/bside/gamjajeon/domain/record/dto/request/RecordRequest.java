package com.bside.gamjajeon.domain.record.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Data
@NoArgsConstructor
public class RecordRequest {

	private Long id;

	private String content;

	@Min(value = 0, message = "감자:0, 고구마:1 값만 입력 가능해요.")
	@Max(value = 1, message = "감자:0, 고구마:1 값만 입력 가능해요.")
	@NotNull(message = "감정은 필수값이예요.")
	private Integer moodType;

	@PastOrPresent(message = "미래의 날짜는 일기를 작성할 수 없어요.")
	@NotNull(message = "일기 작성 일자는 필수값이예요.")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate recordDate;

	@Size(max = 5, message = "해시태그는 최대 5개까지 입력가능해요.")
	private List<String> hashtagList;

}
