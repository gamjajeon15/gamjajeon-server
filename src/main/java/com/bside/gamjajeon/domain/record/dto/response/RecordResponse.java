package com.bside.gamjajeon.domain.record.dto.response;


import lombok.Data;

@Data
public class RecordResponse {
	private Long recordId;

	public RecordResponse(Long recordId) {
		this.recordId = recordId;
	}
}
