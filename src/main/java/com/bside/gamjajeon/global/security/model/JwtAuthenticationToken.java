package com.bside.gamjajeon.global.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String credentials;

    public JwtAuthenticationToken(Object principal, String credentials) {
        super(AuthorityUtils.createAuthorityList("ROLE_USER"));
        super.setAuthenticated(false);

        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
