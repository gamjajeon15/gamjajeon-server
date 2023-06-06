package com.bside.gamjajeon.global.security.filter;

import com.bside.gamjajeon.domain.user.exception.TokenInvalidException;
import com.bside.gamjajeon.global.security.jwt.JwtUtil;
import com.bside.gamjajeon.global.security.model.CustomUserDetails;
import com.bside.gamjajeon.global.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTH_HEADER);
        log.debug("Header =  " + header);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = StringUtils.delete(header, TOKEN_PREFIX).trim();
        String username = jwtUtil.extractUsername(token);
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validateToken(token, userDetails)) {
            throw new TokenInvalidException();
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(userDetails);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getAuthentication(CustomUserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

}
