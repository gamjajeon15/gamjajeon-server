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
                                            @RequestPart("file") MultipartFile multipartFile) throws IOException {
        log.info("Record Create Started with = " + recordRequest.toString());
        return ApiResponse.of(recordService.save(user.getUser(), recordRequest, multipartFile));
    }
}
