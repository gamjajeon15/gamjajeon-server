package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.record.repository.RecordRepository;
import com.bside.gamjajeon.domain.user.dto.request.*;
import com.bside.gamjajeon.domain.user.dto.response.LoginResponse;
import com.bside.gamjajeon.domain.user.dto.response.UsernameResponse;
import com.bside.gamjajeon.domain.user.entity.User;
import com.bside.gamjajeon.domain.user.exception.AccountNotFoundException;
import com.bside.gamjajeon.domain.user.exception.EmailExistException;
import com.bside.gamjajeon.domain.user.exception.EmailNotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomJwtProvider provider;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RecordRepository recordRepository;

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

    public void checkUsername(UsernameRequest usernameRequest) {
        if (userRepository.existsByUsername(usernameRequest.getUsername())) {
            throw new UsernameExistException();
        }
    }

    public void checkEmail(EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail())) {
            throw new EmailExistException();
        }
    }

    public UsernameResponse findUsername(EmailRequest emailRequest) {
        String username = userRepository.findUsernameByEmail(emailRequest.getEmail()).orElseThrow(EmailNotFoundException::new);
        return new UsernameResponse(username);
    }

    @Transactional
    public void resetPassword(User user, @Valid PasswordRequest passwordRequest) {
        if (!userRepository.existsByUsernameAndEmail(passwordRequest.getUsername(), passwordRequest.getEmail())) {
            throw new AccountNotFoundException();
        }

        String newPassword = passwordEncoder.encode(passwordRequest.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Transactional
    public void withdraw(User user) {
        recordRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
