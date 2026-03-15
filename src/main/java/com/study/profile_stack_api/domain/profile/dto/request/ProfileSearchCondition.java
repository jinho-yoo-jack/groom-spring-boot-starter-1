package com.study.profile_stack_api.domain.profile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 검색 상태를 위한 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileSearchCondition {
    private String name = null;
    private String position = null;
    private int page = 0;
    private int size = 0;

    public static ProfileSearchCondition of(String name, String position, int page, int size) {
        return new ProfileSearchCondition(name, position, page, size);
    }
}
