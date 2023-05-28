package com.bside.gamjajeon.domain.user.api;

import com.bside.gamjajeon.domain.user.dto.request.LoginRequest;
import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.service.UserService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Object> signup(@Valid @RequestBody SignupRequest signupRequest) {
        log.info("Signup Request = " + signupRequest.toString());
        userService.signup(signupRequest);
        return ApiResponse.empty();
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login Request = " + loginRequest.toString());
        LoginResponse loginResponse = userService.login(loginRequest);
        return ApiResponse.of(loginResponse);
    }

    /**
     * TODO: 테스트 위해 추가한 API (다음 작업에서 삭제)
     * @AuthenticationPrincipal JwtAuthentication user: 사용자 정보 얻을 때 사용
     */
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal JwtAuthentication user) {
        log.info("User ID = " + user.getUserId() + " Username = " + user.getUsername());
        return "test";
    }

}
