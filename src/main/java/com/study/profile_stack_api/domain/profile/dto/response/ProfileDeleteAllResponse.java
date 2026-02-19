package com.study.profile_stack_api.domain.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

/**
 * 전체 프로필 삭제 응답 DTO
 */
@Getter
@Builder
@JsonPropertyOrder({"message", "deleteCount"})
public class ProfileDeleteAllResponse {
    private String message;
    private Long deleteCount;

    private ProfileDeleteAllResponse() {}

    public static ProfileDeleteAllResponse of(Long count) {
        return ProfileDeleteAllResponse.builder()
                .message("전체 프로필이 성공적으로 삭제되었습니다.")
                .deleteCount(count)
                .build();
    }
}
