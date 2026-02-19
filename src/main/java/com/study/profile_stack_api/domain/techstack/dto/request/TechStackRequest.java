package com.study.profile_stack_api.domain.techstack.dto.request;

import java.time.LocalDateTime;

public class TechStackRequest {

    private long profileId;
    private String name;
    private String category;
    private String categoryIcon;
    private String proficiency;
    private String proficiencyIcon;
    private Integer yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public TechStackRequest() {}

    public boolean hasNoUpdates() {
        return name == null
                && category == null
                && proficiency == null
                && yearsOfExp == null;
    }

    // Getter
    public long getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public String getProficiency() {
        return proficiency;
    }

    public String getProficiencyIcon() {
        return proficiencyIcon;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public void setProficiencyIcon(String proficiencyIcon) {
        this.proficiencyIcon = proficiencyIcon;
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
