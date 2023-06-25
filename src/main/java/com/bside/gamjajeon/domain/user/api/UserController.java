package com.bside.gamjajeon.domain.user.api;

import com.bside.gamjajeon.domain.user.dto.request.*;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.dto.response.TokenResponse;
import com.bside.gamjajeon.domain.user.dto.response.UsernameResponse;
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
        log.debug("Signup Request = " + signupRequest.toString());
        LoginResponse signup = userService.signup(signupRequest);
        return ApiResponse.of(signup);
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("Login Request = " + loginRequest.toString());
        LoginResponse loginResponse = userService.login(loginRequest);
        return ApiResponse.of(loginResponse);
    }

    @PostMapping("/tokens")
    public ApiResponse<Object> token(HttpServletRequest request) {
        TokenResponse tokenResponse = tokenService.generateToken(request);
        return ApiResponse.of(tokenResponse);
    }

    @PostMapping("/check/username")
    public ApiResponse<Object> checkUsername(@Valid @RequestBody UsernameRequest usernameRequest) {
        log.debug("Username Request = " + usernameRequest.toString());
        userService.checkUsername(usernameRequest);
        return ApiResponse.of("사용 가능한 아이디에요");
    }

    @PostMapping("/check/email")
    public ApiResponse<Object> checkEmail(@Valid @RequestBody EmailRequest emailRequest) {
        log.debug("Email Request = " + emailRequest.toString());
        userService.checkEmail(emailRequest);
        return ApiResponse.of("사용 가능한 이메일 주소에요");
    }

    @PostMapping("/find/username")
    public ApiResponse<Object> findUsername(@Valid @RequestBody EmailRequest emailRequest) {
        log.info("Username Request = " + emailRequest.toString());
        UsernameResponse usernameResponse = userService.findUsername(emailRequest);
        return ApiResponse.of(usernameResponse);
    }

    @PostMapping("/reset/password")
    public ApiResponse<Object> findUsername(@Valid @RequestBody PasswordRequest passwordRequest,
                                            @AuthUser CustomUserDetails userDetails) {
        log.info("Password Request = " + passwordRequest.toString());
        userService.resetPassword(userDetails.getUser(), passwordRequest);
        return ApiResponse.empty();
    }
}
