package com.study.profile_stack_api.domain.techstack.dto.request;

import java.time.LocalDateTime;

public class techstackRequest {

    private long profileId;
    private String name;
    private String techCategory;
    private String techIcon;
    private String proficiency;
    private String proficiencyicon;
    private int yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public techstackRequest() {}

    // Getter
    public long getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public String getTechCategory() {
        return techCategory;
    }

    public String getTechIcon() {
        return techIcon;
    }

    public String getProficiency() {
        return proficiency;
    }

    public String getProficiencyicon() {
        return proficiencyicon;
    }

    public int getYearsOfExp() {
        return yearsOfExp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    // Setter
    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTechCategory(String techCategory) {
        this.techCategory = techCategory;
    }

    public void setTechIcon(String techIcon) {
        this.techIcon = techIcon;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public void setProficiencyicon(String proficiencyicon) {
        this.proficiencyicon = proficiencyicon;
    }

    public void setYearsOfExp(int yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
