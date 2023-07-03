package com.bside.gamjajeon.domain.record.api;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.service.RecordService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/records")
@RestController
public class RecordController {
	private final RecordService recordService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/create")
	public ApiResponse<Object> createRecord(@AuthUser CustomUserDetails user,
		@Valid @RequestPart("record") RecordRequest recordRequest,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
		log.info("Record Create Started with = " + recordRequest.toString());
		return ApiResponse.of(recordService.save(user.getUser(), recordRequest, multipartFile));
	}

	@GetMapping("/searchAll")
	public ApiResponse<Object> getMonthRecords(@AuthUser CustomUserDetails user,
		@RequestParam String searchDate) {
		LocalDate localDate = LocalDate.parse(searchDate);
		log.info("One Month Record Searching Started with = " + localDate.toString());
		return ApiResponse.of(recordService.findRecordsAll(localDate));
	}

}
