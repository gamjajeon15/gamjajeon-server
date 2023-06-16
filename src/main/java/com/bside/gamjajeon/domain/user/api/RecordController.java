package com.bside.gamjajeon.domain.user.api;

import com.bside.gamjajeon.domain.user.dto.request.RecordRequest;
import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.service.RecordService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/records")
@RestController
public class RecordController {
    private final RecordService recordService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Object> createRecord(@Valid @RequestBody RecordRequest recordRequest) {
        log.debug("Record Create Started with = " + recordRequest.toString());
        recordService.save(recordRequest);
        return ApiResponse.empty();
    }
}
