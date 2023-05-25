package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.request.SignupRequest;
import com.bside.gamjajeon.domain.user.mapper.UserMapper;
import com.bside.gamjajeon.domain.user.repository.UserRepository;
import com.bside.gamjajeon.domain.user.exception.UserExistException;
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
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserExistException();
        }

        userRepository.save(userMapper.toUser(signupRequest));
    }
}
