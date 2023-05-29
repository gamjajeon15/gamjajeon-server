package com.bside.gamjajeon.domain.user.service;

import com.bside.gamjajeon.domain.user.dto.response.TokenResponse;
import com.bside.gamjajeon.domain.user.exception.TokenInvalidException;
import com.bside.gamjajeon.global.security.jwt.JwtUtil;
import com.bside.gamjajeon.global.security.model.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class TokenService {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public TokenResponse generateToken(HttpServletRequest request) {
        String token = extractToken(request);
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        validateToken(token, userDetails);
        Jwt jwt = jwtUtil.generateJwt(userDetails);
        return TokenResponse.of(jwt);
    }

    private static String extractToken(HttpServletRequest request) {
        String header = request.getHeader(TOKEN_HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new TokenInvalidException();
        }
        return StringUtils.delete(header, TOKEN_PREFIX).trim();
    }

    private void validateToken(String token, UserDetails userDetails) {
        if (!jwtUtil.validateToken(token, userDetails)) {
            throw new TokenInvalidException();
        }
    }
}
