package com.study.profile_stack_api.domain.profile.dto.request;
import com.study.profile_stack_api.domain.profile.entity.Position;

import java.time.LocalDateTime;

public record ProfileUpdateRequest(
        Long id,
        String name,
        String email,
        String bio,
        String position,
        Integer careerYears,
        String githubUrl,
        String blogUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * 모든 필드가 null인지 확인
     * 아무것도 수정할 내용이 없는 경우 체크용
     */
    public boolean hasNoUpdates(){
        return name == null
                && email == null
                && bio == null
                && position == null
                && careerYears == null
                && githubUrl == null
                && blogUrl == null;
    }
}