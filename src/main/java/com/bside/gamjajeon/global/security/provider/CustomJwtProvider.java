package com.bside.gamjajeon.global.security.provider;

import com.bside.gamjajeon.global.security.jwt.JwtUtil;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import com.bside.gamjajeon.global.security.model.Jwt;
import com.bside.gamjajeon.global.security.model.JwtAuthenticationToken;
import com.bside.gamjajeon.global.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomJwtProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Jwt jwt = jwtUtil.generateJwt(userDetails);
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, null);
            jwtAuthenticationToken.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
            return jwtAuthenticationToken;
        }

        throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

