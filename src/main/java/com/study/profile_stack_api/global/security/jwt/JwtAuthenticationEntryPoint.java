package com.study.profile_stack_api.global.security.jwt;

import com.study.profile_stack_api.domain.auth.exception.ExpiredTokenException;
import com.study.profile_stack_api.domain.auth.exception.InvalidTokenException;
import com.study.profile_stack_api.global.common.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 인증되지 않은 요청(401)이 발생했을 때 JSON 형태의 에러 응답을 내려주는 EntryPoint.
 *
 * JwtAuthenticationFilter가 request attribute("exception")에 저장한 예외를 읽어
 * 만료 토큰 / 유효하지 않은 토큰 / 일반 인증 실패를 구분해서 응답한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        log.error("Unauthorized access attempt: {}", authException.getMessage());

        // JwtAuthenticationFilter에서 request.setAttribute("exception", e)로 저장한 예외 확인
        // -> TOKEN_EXPIRED, INVALID_TOKEN 등을 구분하여 응답
        Exception exception = (Exception) request.getAttribute("exception");

        String errorCode;
        String errorMessage;

        // 예외 타입에 따라 다른 에러 코드와 메시지를 설정
        if (exception instanceof ExpiredTokenException) {
            errorCode = "TOKEN_EXPIRED";
            errorMessage = exception.getMessage();
        } else if (exception instanceof InvalidTokenException) {
            errorCode = "INVALID_TOKEN";
            errorMessage = exception.getMessage();
        } else {
            errorCode = "UNAUTHORIZED";
            errorMessage = "Authentication is required to access this resource";
        }

        // ApiResponse 형식으로 에러 응답 생성
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode, errorMessage);

        // JSON 응답 반환
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // json 에러 응답 작성
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
