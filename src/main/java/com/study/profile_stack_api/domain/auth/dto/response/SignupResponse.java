package com.study.profile_stack_api.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResponse {

    private Long userId;
    private String username;
    private String message;

    /**
     * 회원가입 성공 메시지를 포함한 응답 객체 생성
     */
    public static SignupResponse of(Long userId, String username) {
        return SignupResponse.builder()
                .userId(userId)
                .username(username)
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
    }
}
