package com.bside.gamjajeon.global.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bside.gamjajeon.global.dto.ErrorResponse;
import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setResponse(response, ErrorCode.TOKEN_EXPIRED, true);
        } catch (GeneralException e) {
            setResponse(response, e.getErrorCode(), false);
        } catch (HttpMessageNotReadableException e) {
            setResponse(response, ErrorCode.VALIDATION_ERROR, false);
        } catch (SignatureException | MalformedJwtException e) {
            setResponse(response, ErrorCode.TOKEN_INVALID, false);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode, boolean isTokenExpired) throws IOException {
        int status = isTokenExpired ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_BAD_REQUEST;
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
                .write(mapper.writeValueAsString(ErrorResponse.of(errorCode)));
    }

}
