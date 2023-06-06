package com.bside.gamjajeon.domain.user.api;

import com.bside.gamjajeon.domain.user.dto.request.LoginRequest;
import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.dto.response.SignupResponse;
import com.bside.gamjajeon.domain.user.dto.response.TokenResponse;
import com.bside.gamjajeon.domain.user.service.TokenService;
import com.bside.gamjajeon.domain.user.service.UserService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import com.bside.gamjajeon.global.security.model.AuthUser;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Object> signup(@Valid @RequestBody SignupRequest signupRequest) {
        log.info("Signup Request = " + signupRequest.toString());
        SignupResponse signup = userService.signup(signupRequest);
        return ApiResponse.of(signup);
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login Request = " + loginRequest.toString());
        LoginResponse loginResponse = userService.login(loginRequest);
        return ApiResponse.of(loginResponse);
    }

    @PostMapping("/tokens")
    public ApiResponse<Object> token(HttpServletRequest request) {
        TokenResponse tokenResponse = tokenService.generateToken(request);
        return ApiResponse.of(tokenResponse);
    }

    /**
     * TODO: 테스트 위해 추가한 API (다음 작업에서 삭제)
     */
    @GetMapping("/test")
    public String test(@AuthUser CustomUserDetails user) {
        return "test";
    }

}
