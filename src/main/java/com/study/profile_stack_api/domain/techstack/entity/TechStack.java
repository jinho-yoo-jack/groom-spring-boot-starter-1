package com.study.profile_stack_api.domain.techstack.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 기술 스택 Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {
    private Long id;
    private Long profileId;
    private String name;
    private TechCategory category;
    private Proficiency proficiency;
    private Integer yearsOfExp;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();;

    /**
     * 기술 스택 정보 수정
     * Null이 아닌 값만 업데이트
     */
    public void update(String name, TechCategory category, Proficiency proficiency, Integer yearsOfExp) {
        // Null이 아닌 경우에만 업데이트
        if (name != null) {
            this.name = name;
        }
        if (category != null) {
            this.category = category;
        }
        if (proficiency != null) {
            this.proficiency = proficiency;
        }
        if (yearsOfExp != null) {
            this.yearsOfExp = yearsOfExp;
        }
    }
}
