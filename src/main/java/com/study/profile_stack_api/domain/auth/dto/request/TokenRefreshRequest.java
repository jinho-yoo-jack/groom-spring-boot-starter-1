package com.study.profile_stack_api.domain.auth.dto.request;


import com.study.profile_stack_api.global.validation.NotBlankIfPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰 재발급 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRefreshRequest {

    @NotBlankIfPresent(message = "리프레시 토큰은 필수입니다.")
    private String refreshToken;
}
