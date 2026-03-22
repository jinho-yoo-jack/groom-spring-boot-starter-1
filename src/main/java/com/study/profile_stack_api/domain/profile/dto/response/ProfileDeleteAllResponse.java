package com.study.profile_stack_api.domain.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전체 프로필 삭제 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"message", "deleteCount"})
public class ProfileDeleteAllResponse {
    private String message;
    private Long deleteCount;

    public static ProfileDeleteAllResponse of(Long count) {
        return ProfileDeleteAllResponse.builder()
                .message("전체 프로필이 성공적으로 삭제되었습니다.")
                .deleteCount(count)
                .build();
    }
}
