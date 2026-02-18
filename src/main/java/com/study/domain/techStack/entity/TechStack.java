package com.study.domain.techStack.entity;
import java.time.LocalDateTime;

public class TechStack {
    private Long id;              // PK, Auto Increment
    private Long profileId;       // FK (Not Null)
    private String name;          // Not Null, max 50
    private String category;      // Not Null
    private String proficiency;   // Not Null
    private Integer yearsOfExp;   // Not Null, >= 0
    private LocalDateTime createdAt;  // 자동 생성
    private LocalDateTime updatedAt;  // 자동 갱신

    // 기본 생성자
    public TechStack() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 전체 생성자
    public TechStack(Long id, Long profileId, String name, String category,
                     String proficiency, Integer yearsOfExp,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.profileId = profileId;
        this.name = name;
        this.category = category;
        this.proficiency = proficiency;
        this.yearsOfExp = yearsOfExp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 부분 업데이트 메서드
    public void update(String name, String category,
                       String proficiency, Integer yearsOfExp) {

        if (name != null) {
            this.name = name;
        }
        if (category != null) {
            this.category = category;
        }
        if (proficiency != null) {
            this.proficiency = proficiency;
        }
        if (yearsOfExp != null && yearsOfExp >= 0) {
            this.yearsOfExp = yearsOfExp;
        }

        // 수정 시간 갱신
        this.updatedAt = LocalDateTime.now();
    }

    // Getter
    public Long getId() { return id; }
    public Long getProfileId() { return profileId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getProficiency() { return proficiency; }
    public Integer getYearsOfExp() { return yearsOfExp; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter
    public void setId(Long id) { this.id = id; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setProficiency(String proficiency) { this.proficiency = proficiency; }
    public void setYearsOfExp(Integer yearsOfExp) {
        if (yearsOfExp != null && yearsOfExp >= 0) {
            this.yearsOfExp = yearsOfExp;
        }
    }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
