package com.study.profile_stack_api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Token Response DTO
 * Contains new access token and refresh token
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRefreshResponse {

    private String accessToken;

    /**
     * Create token response with default token type
     */
    public static TokenRefreshResponse of(String accessToken) {
        return TokenRefreshResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
