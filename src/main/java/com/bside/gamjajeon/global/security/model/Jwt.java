package com.bside.gamjajeon.global.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Jwt {
    private String accessToken;
    private String refreshToken;
}
