package com.study.domain.techStack.dto.request;

public class TechStackCreateRequest {
    private String name;        // required
    private String category;    // required
    private String proficiency; // required
    private Integer yearsOfExp; // required (>=0)

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
}
