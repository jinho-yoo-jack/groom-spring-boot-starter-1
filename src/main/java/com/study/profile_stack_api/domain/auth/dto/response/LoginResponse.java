package com.study.profile_stack_api.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String username;

    /**
     * 기본 토큰 타입을 포함한 로그인 응답 객체 생성
     */
    public static LoginResponse of(
            String accessToken,
            String refreshToken,
            Long expiresIn,
            String username
    ) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .username(username)
                .build();
    }
}
