package com.bside.gamjajeon.domain.record.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.service.RecordService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/records")
@RestController
public class RecordController {
	private final RecordService recordService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<Object> createRecord(@AuthUser CustomUserDetails user,
		@Valid @RequestBody RecordRequest recordRequest) {
		log.debug("Record Create Started with = " + recordRequest.toString());
		return ApiResponse.of(recordService.save(user.getUser(), recordRequest));
	}
}
