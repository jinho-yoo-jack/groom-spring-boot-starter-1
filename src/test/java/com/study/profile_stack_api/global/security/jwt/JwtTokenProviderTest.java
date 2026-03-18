package com.study.profile_stack_api.global.security.jwt;

import com.study.profile_stack_api.domain.auth.exception.ExpiredTokenException;
import com.study.profile_stack_api.domain.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JwtTokenProviderTest {
    private JwtTokenProvider tokenProvider;
    private final String SECRET_KEY = "test-secret-key-for-jwt-token-generation-must-be-longer-than-256-bits";

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider(
                SECRET_KEY,
                1_800_000,
                604_800_000
        );
    }

    @Test
    @DisplayName("액세스 토큰을 생성할 수 있다.")
    void createAccessToken_success() {
        // token이 null이 아닌지 검증

        // Given
        String username = "testuser";
        String role = "ROLE_USER";

        // When
        String token = tokenProvider.createAccessToken(username, role);

        // Then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("토큰에서 username을 추출할 수 있다.")
    void extractUsername_success() {
        // 추출한 username이 원본과 같은지 검증

        // Given
        String username = "testuser";
        String role = "ROLE_USER";
        String token = tokenProvider.createAccessToken(username, role);

        // When
        String extracted = tokenProvider.getUsername(token);

        // Then
        assertThat(extracted).isEqualTo(username);
    }

    @Test
    @DisplayName("만료된 토큰은 유효하지 않다.")
    void validateToken_expired() {
        // 만료 시간이 0인 TokenProvider를 생성하고 토큰 검증 테스트

        // Given
        JwtTokenProvider anotherProvider  = new JwtTokenProvider(
                SECRET_KEY,
                0, // 액세스 토큰 즉시 만료
                604_800_000
        );

        String username = "testuser";
        String role = "ROLE_USER";
        String token = anotherProvider.createAccessToken(username, role);

        // When & Then
        assertThatThrownBy(() -> tokenProvider.validateToken(token))
                .isInstanceOf(ExpiredTokenException.class)
                .hasMessage("인증 토큰이 만료되었습니다.");
    }

    @Test
    @DisplayName("잘못된 시그니처의 토큰은 유효하지 않다.")
    void validateToken_invalidSignature() {
        // 다른 secret key로 생성된 토큰 검증 시 예외 발생 테스트

        // Given
        JwtTokenProvider anotherProvider = new JwtTokenProvider(
                "another-secret-key-for-jwt-token-generation-must-be-longer-than-256-bits",
                1_800_000,
                604_800_000
        );

        String username = "testuser";
        String role = "ROLE_USER";
        String token = anotherProvider.createAccessToken(username, role);

        // When & Then
        assertThatThrownBy(() -> tokenProvider.validateToken(token))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("인증 토큰 서명이 올바르지 않습니다.");
    }
}
