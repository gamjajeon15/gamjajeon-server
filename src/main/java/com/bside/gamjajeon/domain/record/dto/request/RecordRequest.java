package com.bside.gamjajeon.domain.record.dto.request;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordRequest {

	private Long recordId;

	private String content;

	@Min(value = 0, message = "감자:0, 고구마:1 값만 입력 가능해요.")
	@Max(value = 1, message = "감자:0, 고구마:1 값만 입력 가능해요.")
	private int moodType;

	@NotNull(message = "일기 작성 일자는 필수값이예요.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date recordDate;

	@Size(max = 5, message = "해시태그는 최대 5개까지 입력가능해요.")
	private List<String> hashtagList;
}
