package com.study.profile_stack_api.domain.techstack.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TechStackDeleteAllResponse {
    private String message;
    private Long deletedCount;

    public static TechStackDeleteAllResponse of(Long deletedCount) {
        return TechStackDeleteAllResponse.builder()
                .message("전체 기술 스택이 성공적으로 삭제되었습니다.")
                .deletedCount(deletedCount)
                .build();
    }
}
