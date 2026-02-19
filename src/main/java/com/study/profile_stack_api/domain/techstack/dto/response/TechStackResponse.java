package com.study.profile_stack_api.domain.techstack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;

import java.time.LocalDateTime;

public class TechStackResponse {
    private Long id;
    private Long profileId;
    private String name;
    private TechCategory category;
    private String categoryIcon;
    private Proficiency proficiency;
    private String proficiencyIcon;
    private Integer yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public TechStackResponse() {}

    // 모든 필드를 포함한 생성자
    public TechStackResponse(TechStack techStack) {
        this.id = techStack.getId();
        this.profileId = techStack.getProfileId();
        this.name = techStack.getName();
        this.category = techStack.getCategory();
        this.categoryIcon = techStack.getCategory().getIcon();
        this.proficiency = techStack.getProficiency();
        this.proficiencyIcon = techStack.getProficiency().getIcon();
        this.yearsOfExp = techStack.getYearsOfExp();
        this.createdAt = techStack.getCreatedAt();
        this.updatedAt = techStack.getUpdatedAt();
    }

    public static TechStackResponse from(TechStack techStack) {
        TechStackResponse response =  new TechStackResponse(techStack);

        response.setCategoryIcon(techStack.getCategory().getIcon());
        response.setProficiencyIcon(techStack.getProficiency().getIcon());

        return response;
    }

    // Getter 및 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TechCategory getCategory() { return category; }
    public void setCategory(TechCategory category) { this.category = category; }

    public String getCategoryIcon() { return categoryIcon; }
    public void setCategoryIcon(String categoryIcon) { this.categoryIcon = categoryIcon; }

    public Proficiency getProficiency() { return proficiency; }
    public void setProficiency(Proficiency proficiency) { this.proficiency = proficiency; }

    public String getProficiencyIcon() { return proficiencyIcon; }
    public void setProficiencyIcon(String proficiencyIcon) { this.proficiencyIcon = proficiencyIcon; }

    public Integer getYearsOfExp() { return yearsOfExp; }
    public void setYearsOfExp(Integer yearsOfExp) { this.yearsOfExp = yearsOfExp; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}
