package com.study.profile_stack_api.domain.techstack.dto.request;

import lombok.*;

/**
 * 기술 스택 생성 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStackCreateRequest {
    private Long profileId;
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;
}
