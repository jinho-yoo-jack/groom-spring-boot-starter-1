package com.study.profile_stack_api.domain.techstack.dto.request;

import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;

public class TechStackUpdateRequest {

    private String name;
    private TechCategory category;
    private Proficiency proficiency;
    private Integer yearsOfExp;

    public TechStackUpdateRequest() {}

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TechCategory getCategory() { return category; }
    public void setCategory(TechCategory category) { this.category = category; }

    public Proficiency getProficiency() { return proficiency; }
    public void setProficiency(Proficiency proficiency) { this.proficiency = proficiency; }

    public Integer getYearsOfExp() { return yearsOfExp; }
    public void setYearsOfExp(Integer yearsOfExp) { this.yearsOfExp = yearsOfExp; }

    // Profile DTO에 있던 형식만 맞춰서 추가
    public boolean hasNoUpdates() {
        return name == null
                && category == null
                && proficiency == null
                && yearsOfExp == null;
    }

}
