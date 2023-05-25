package com.bside.gamjajeon.domain.user.api;

import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.service.UserService;
import com.bside.gamjajeon.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Object> signup(@Valid @RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ApiResponse.empty();
    }


}
