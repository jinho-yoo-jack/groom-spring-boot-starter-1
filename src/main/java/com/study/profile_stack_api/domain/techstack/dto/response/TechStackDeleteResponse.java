package com.study.profile_stack_api.domain.techstack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

/**
 * 기술 스택 삭제 응답 DTO
 */
@Getter
@Builder
@JsonPropertyOrder({"message", "deletedId"})
public class TechStackDeleteResponse {
    private String message;
    private Long deletedId;

    public static TechStackDeleteResponse of(Long id, Boolean isDeleted) {
        if (isDeleted) {
            return TechStackDeleteResponse.builder()
                    .message("기술 스택이 성공적으로 삭제되었습니다.")
                    .deletedId(id)
                    .build();
        } else {
            return TechStackDeleteResponse.builder()
                    .message("기술 스택 삭제가 실패했습니다.")
                    .build();
        }
    }
}
