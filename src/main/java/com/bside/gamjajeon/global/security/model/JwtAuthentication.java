package com.bside.gamjajeon.global.security.model;

import com.bside.gamjajeon.domain.user.exception.UserNotFoundException;
import lombok.Getter;

import java.util.Objects;

public class JwtAuthentication {
    private final String token;

    @Getter
    private final Long userId;

    @Getter
    private final String username;

    public JwtAuthentication(final String token, final Long userId, final String username) {
        if (Objects.isNull(token))
            throw new UserNotFoundException();
        if (Objects.isNull(userId))
            throw new UserNotFoundException();
        if (Objects.isNull(username))
            throw new UserNotFoundException();

        this.token = token;
        this.userId = userId;
        this.username = username;
    }

}
