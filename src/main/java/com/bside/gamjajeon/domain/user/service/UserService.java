package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.request.LoginRequest;
import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.exception.EmailExistException;
import com.bside.gamjajeon.domain.user.exception.UsernameExistException;
import com.bside.gamjajeon.domain.user.mapper.UserMapper;
import com.bside.gamjajeon.domain.user.repository.UserRepository;
import com.bside.gamjajeon.global.security.jwt.JwtUtil;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import com.bside.gamjajeon.global.security.model.Jwt;
import com.bside.gamjajeon.global.security.provider.CustomJwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomJwtProvider provider;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse signup(SignupRequest signupRequest) {
        validate(signupRequest);
        userRepository.save(userMapper.toUser(signupRequest));
        return getLoginResponse(signupRequest.getUsername(), signupRequest.getPassword());
    }

    private void validate(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UsernameExistException();
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailExistException();
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return getLoginResponse(loginRequest.getUsername(), loginRequest.getPassword());
    }

    private LoginResponse getLoginResponse(String username, String password) {
        Authentication authenticate = authenticateUser(username, password);
        CustomUserDetails details = (CustomUserDetails) authenticate.getDetails();
        Jwt jwt = jwtUtil.generateJwt(details);
        return new LoginResponse(jwt.getAccessToken(), jwt.getRefreshToken(), details.getId());
    }

    private Authentication authenticateUser(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return provider.authenticate(authenticationToken);
    }

}
