package com.study.profile_stack_api.domain.techstack.dto.response;

import java.time.LocalDateTime;

public class techstackResponse {

    private long profileId;
    private String name;
    private String techCategory;
    private String techIcon;
    private String proficiency;
    private String proficiencyicon;
    private int yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public techstackResponse() {}

    // Getter & Setter
    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechCategory() {
        return techCategory;
    }

    public void setTechCategory(String techCategory) {
        this.techCategory = techCategory;
    }

    public String getTechIcon() {
        return techIcon;
    }

    public void setTechIcon(String techIcon) {
        this.techIcon = techIcon;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getProficiencyicon() {
        return proficiencyicon;
    }

    public void setProficiencyicon(String proficiencyicon) {
        this.proficiencyicon = proficiencyicon;
    }

    public int getYearsOfExp() {
        return yearsOfExp;
    }

    public void setYearsOfExp(int yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
