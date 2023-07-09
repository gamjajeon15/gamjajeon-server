package com.bside.gamjajeon.domain.record.api;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bside.gamjajeon.domain.record.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.record.service.RecordService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/records")
@RestController
public class RecordController {
	private final RecordService recordService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<Object> createRecord(@AuthUser CustomUserDetails user,
		@Valid @RequestPart("record") RecordRequest recordRequest,
		@RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
		log.info("Record Create Started with = " + recordRequest.toString());
		return ApiResponse.of(recordService.save(user.getUser(), recordRequest, multipartFile));
	}

	@GetMapping("/{searchDate}")
	public ApiResponse<Object> getMonthRecords(@AuthUser CustomUserDetails user,
		@PathVariable String searchDate) {
		LocalDate localDate = LocalDate.parse(searchDate);
		log.info("One Month Record Searching Started with = " + localDate.toString());
		return ApiResponse.of(recordService.findRecordsAll(user.getUser(), localDate));
	}

<<<<<<< HEAD
	@GetMapping("/mood")
	public ApiResponse<Object> getMoodStatistics(@AuthUser CustomUserDetails userDetails, @RequestParam Integer year) {
		return ApiResponse.of(recordService.getMoodStatistics(userDetails.getUser(), year));
	}

	@DeleteMapping("/{recordId}")
	public ApiResponse<Object> deleteRecord(@AuthUser CustomUserDetails user,
		@PathVariable Integer recordId) {
=======
	@DeleteMapping("/{recordId}")
	public ApiResponse<Object> deleteRecord(@AuthUser CustomUserDetails user,
<<<<<<< HEAD
		@PathVariable Integer recordId){
>>>>>>> ecf6158 (fix : detect method and modify controller url)
=======
		@PathVariable Integer recordId) {
>>>>>>> 4f44e1f (fix : coding convention)
		log.info("Record Delete Started with = " + recordId.toString());
		recordService.deleteRecord(user.getUser(), recordId);
		return ApiResponse.of(recordId);
	}

}
