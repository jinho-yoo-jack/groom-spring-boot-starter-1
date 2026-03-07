package com.study.profile_stack_api.domain.tech_stack.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TechStackUpdateRequest {
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;

    public boolean hasNoUpdates() {
        return name == null &&
                category == null &&
                proficiency == null &&
                yearsOfExp == null;
    }
}
