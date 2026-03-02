package com.study.profile_stack_api.domain.techstack.dto.request;


import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.global.validation.EnumValid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TechStackUpdateRequest {

    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    private String name;

    @EnumValid(enumClass = TechCategory.class, allowNull = true, message = "기술 카테고리는 LANGUAGE, FRAMEWORK, DATABASE, DEVOPS, TOOL, ETC 중 하나여야 합니다.")
    private String category;

    @EnumValid(enumClass = Proficiency.class, allowNull = true, message = "숙련도는 BEGINNER, INTERMEDIATE, ADVANCED, EXPERT 중 하나여야 합니다.")
    private String proficiency;

    @Min(value = 0, message = "사용경험은 0이상이여야 합니다.")
    private Integer yearsOfExp;

    public boolean hasNoUpdates() {
        return name == null
                && category == null
                && proficiency == null
                && yearsOfExp == null;
    }
}
