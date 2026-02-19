package com.study.profile_stack_api.domain.profile.dto.request;

import lombok.Getter;

/**
 * 프로필 검색 상태를 위한 DTO
 */
@Getter
public class ProfileSearchCondition {
    private String name = null;
    private String position = null;
    private int page = 0;
    private int size = 0;

    public ProfileSearchCondition(String name, String position, int page, int size) {
        if (name != null) {
            this.name = name;
        }
        if (position != null) {
            this.position = position;
        }
        if (page != 0) {
            this.page = page;
        }
        if (size != 0) {
            this.size = size;
        }
    }
}
