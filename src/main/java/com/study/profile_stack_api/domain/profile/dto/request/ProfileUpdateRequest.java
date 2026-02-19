package com.study.profile_stack_api.domain.profile.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 프로필 수정 요청 DTO
 */
@Getter
@Setter
public class ProfileUpdateRequest {
    private String name;
    private String email;
    private String bio;
    private String position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;

    /**
     * 모든 필드가 Null인지 확인
     * 아무것도 수정할 내용이 없는 경우 체크용
     */
    public boolean hashNoUpdates() {
        return name == null
                && email == null
                && bio == null
                && position == null
                && careerYears == null
                && githubUrl == null
                && blogUrl == null;
    }
}
