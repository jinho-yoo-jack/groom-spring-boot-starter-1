package com.study.profile_stack_api.domain.techstack.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 기술 스택 수정 요청 DTO
 */
@Getter
@Setter
public class TechStackUpdateRequest {
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;

    /**
     * 모든 필드가 Null인지 확인
     * 아무것도 수정할 내용이 없는 경우 체크용
     */
    public boolean hashNoUpdates() {
        return name == null
                && category == null
                && proficiency == null
                && yearsOfExp == null;
    }
}
