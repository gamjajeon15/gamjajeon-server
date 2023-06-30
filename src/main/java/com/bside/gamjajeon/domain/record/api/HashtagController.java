package com.bside.gamjajeon.domain.record.api;

import com.bside.gamjajeon.domain.record.dto.response.HashtagResponse;
import com.bside.gamjajeon.domain.record.service.HashtagService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
