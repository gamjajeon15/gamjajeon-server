package com.bside.gamjajeon.global.security.service;

import com.bside.gamjajeon.domain.user.entity.User;
import com.bside.gamjajeon.domain.user.exception.UserNotFoundException;
import com.bside.gamjajeon.domain.user.repository.UserRepository;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new CustomUserDetails(user);
    }
}
