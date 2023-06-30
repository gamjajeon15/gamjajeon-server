package com.bside.gamjajeon.domain.user.dto.response;

import com.bside.gamjajeon.global.security.model.Jwt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public static TokenResponse of(Jwt jwt) {
        return new TokenResponse(jwt.getAccessToken(), jwt.getRefreshToken());
    }
}
