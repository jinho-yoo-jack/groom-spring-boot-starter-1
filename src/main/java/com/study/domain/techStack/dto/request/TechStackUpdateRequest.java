package com.study.domain.techStack.dto.request;

public class TechStackUpdateRequest {
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;

    // Getter
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getProficiency() { return proficiency; }
    public Integer getYearsOfExp() { return yearsOfExp; }

    // Setter (Jackson 역직렬화용)
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setProficiency(String proficiency) { this.proficiency = proficiency; }
    public void setYearsOfExp(Integer yearsOfExp) { this.yearsOfExp = yearsOfExp; }

    public boolean hasNoUpdates() {
        return name == null
                && category == null
                && proficiency == null
                && yearsOfExp == null;
    }
}
