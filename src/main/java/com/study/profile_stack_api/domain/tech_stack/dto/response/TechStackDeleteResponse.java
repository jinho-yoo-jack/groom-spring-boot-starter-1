package com.study.profile_stack_api.domain.tech_stack.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class TechStackDeleteResponse {
    private final Long id;
    @Builder.Default
    private final String message = "기술 스택이 정상적으로 삭제되었습니다";

    public static TechStackDeleteResponse of(Long id) {
        return TechStackDeleteResponse.builder()
                .id(id)
                .build();
    }
}
