package com.study.profile_stack_api.domain.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 삭제 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"message", "deletedId"})
public class ProfileDeleteResponse {
    private String message;
    private Long deletedId;

    public static ProfileDeleteResponse of(Long id, Boolean isDeleted) {
        if (isDeleted) {
            return ProfileDeleteResponse.builder()
                    .message("프로필이 성공적으로 삭제되었습니다.")
                    .deletedId(id)
                    .build();
        } else {
            return ProfileDeleteResponse.builder()
                    .message("프로필 삭제가 실패했습니다.")
                    .build();
        }
    }
}
