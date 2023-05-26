package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.exception.UsernameExistException;
import com.bside.gamjajeon.domain.user.mapper.UserMapper;
import com.bside.gamjajeon.domain.user.repository.UserRepository;
import com.bside.gamjajeon.domain.user.exception.EmailExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        validate(signupRequest);
        userRepository.save(userMapper.toUser(signupRequest));
    }

    private void validate(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UsernameExistException();
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailExistException();
        }
    }
}
