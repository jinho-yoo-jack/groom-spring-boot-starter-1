package com.study.profile_stack_api.domain.techstack.entity;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TechStack {

    private long id;
    private long profileId;             // not null
    private String name;                // not null
    private TechCategory techCategory;  // not null
    private Proficiency proficiency;      // not null
    private Integer yearsOfExp;         // not null

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void updateName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTechCategory(TechCategory techCategory) {
        this.techCategory = techCategory;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateYearsOfExp(Integer yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
        this.updatedAt = LocalDateTime.now();
    }
}
