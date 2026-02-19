package com.study.profile_stack_api.domain.techstack.dto.request;

import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;


public class TechStackCreateRequest {
    private Long profileId;
    private String name;
    private TechCategory category;
    private Proficiency proficiency;
    private Integer yearsOfExp;


    public TechStackCreateRequest() {
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TechCategory getCategory() {
        return category;
    }

    public void setCategory(TechCategory category) {
        this.category = category;
    }

    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
    }

    public Integer getYearsOfExp() {
        return yearsOfExp;
    }

    public void setYearsOfExp(Integer yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }
}
