package com.study.domain.techStack.dto.response;

import com.study.domain.techStack.entity.Proficiency;
import com.study.domain.techStack.entity.TechCategory;
import com.study.domain.techStack.entity.TechStack;

import java.time.LocalDateTime;

public class TechStackResponse {

    // ====== 일반 응답(생성/조회/수정) ======
    private Long id;
    private Long profileId;

    private String name;

    private String category;       // enum name
    private String categoryDesc;   // "프로그래밍 언어"
    private String categoryIcon;   // "📝"

    private String proficiency;       // enum name
    private String proficiencyDesc;   // "입문"
    private String proficiencyIcon;   // "🌱"

    private Integer yearsOfExp;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ====== 삭제 응답 ======
    private String message;
    private Long deletedId;

    // ====== Factory: 일반 응답 ======
    public static TechStackResponse from(TechStack t) {
        TechCategory c = TechCategory.from(t.getCategory());
        Proficiency p = Proficiency.from(t.getProficiency());

        TechStackResponse r = new TechStackResponse();
        r.id = t.getId();
        r.profileId = t.getProfileId();
        r.name = t.getName();

        r.category = c.name();
        r.categoryDesc = c.getDescription();
        r.categoryIcon = c.getIcon();

        r.proficiency = p.name();
        r.proficiencyDesc = p.getDescription();
        r.proficiencyIcon = p.getIcon();

        r.yearsOfExp = t.getYearsOfExp();
        r.createdAt = t.getCreatedAt();
        r.updatedAt = t.getUpdatedAt();

        return r;
    }

    // ====== Factory: 삭제 응답 ======
    public static TechStackResponse ofDeleted(Long id) {
        TechStackResponse r = new TechStackResponse();
        r.message = "기술 스택이 정상적으로 삭제되었습니다.";
        r.deletedId = id;
        return r;
    }

    // ====== Getter ======
    public Long getId() { return id; }
    public Long getProfileId() { return profileId; }
    public String getName() { return name; }

    public String getCategory() { return category; }
    public String getCategoryDesc() { return categoryDesc; }
    public String getCategoryIcon() { return categoryIcon; }

    public String getProficiency() { return proficiency; }
    public String getProficiencyDesc() { return proficiencyDesc; }
    public String getProficiencyIcon() { return proficiencyIcon; }

    public Integer getYearsOfExp() { return yearsOfExp; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public String getMessage() { return message; }
    public Long getDeletedId() { return deletedId; }
}
