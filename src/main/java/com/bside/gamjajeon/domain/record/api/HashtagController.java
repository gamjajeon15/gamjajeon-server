package com.bside.gamjajeon.domain.record.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
import com.bside.gamjajeon.domain.record.service.HashtagService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/hashtags")
@RestController
public class HashtagController {
	private final HashtagService hashtagService;

	@GetMapping
	public ApiResponse<Object> getHashtagList(@AuthUser CustomUserDetails userDetails) {
		HashtagResponse hashtagResponse = hashtagService.getHashtagList(userDetails.getUser());
		return ApiResponse.of(hashtagResponse);
	}

	@GetMapping("/weekly")
	public ApiResponse<Object> getWeeklyHashtagList(@AuthUser CustomUserDetails userDetails,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ApiResponse.of(hashtagService.getWeeklyHashtagList(userDetails.getUser(), startDate, endDate));
	}
}
