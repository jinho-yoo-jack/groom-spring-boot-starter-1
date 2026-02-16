package com.study.profile_stack_api.domain.techstack.entity;

import java.time.LocalDateTime;

public class TechStack {

    private long id;
    private long profileId;    // not null
    private String name;        //not null
    private TechCategory techCategory;
    private Proficency proficency;
    private Integer yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TechStack() {};

    // Gegger & Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public TechCategory getTechCategory() {
        return techCategory;
    }

    public void setTechCategory(TechCategory techCategory) {
        this.techCategory = techCategory;
    }

    public Proficency getProficency() {
        return proficency;
    }

    public void setProficency(Proficency proficency) {
        this.proficency = proficency;
    }

    public Integer getYearsOfExp() {
        return yearsOfExp;
    }

    public void setYearsOfExp(Integer yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
