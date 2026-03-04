package com.study.profile_stack_api.domain.profile.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ProfileDeleteResponse {
    private final Long id;
    @Builder.Default
    private final String message = "프로필이 정상적으로 삭제되었습니다";

    public static ProfileDeleteResponse of(Long id) {
        return ProfileDeleteResponse.builder()
                .id(id)
                .build();
    }
}
