package com.study.domain.profile.dto.response;

import com.study.domain.profile.entity.Position;
import com.study.domain.profile.entity.Profile;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;

    private String position;        // enum name (BACKEND 등)
    private String positionDesc;    // "백엔드 개발자"
    private String positionIcon;    // "⚙️"

    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProfileResponse from(Profile p) {
        Position pos = Position.from(p.getPosition());

        ProfileResponse r = new ProfileResponse();
        r.id = p.getId();
        r.name = p.getName();
        r.email = p.getEmail();
        r.bio = p.getBio();
        r.position = pos.name();
        r.positionDesc = pos.getDescription();
        r.positionIcon = pos.getIcon();
        r.careerYears = p.getCareerYears();
        r.githubUrl = p.getGithubUrl();
        r.blogUrl = p.getBlogUrl();
        r.createdAt = p.getCreatedAt();
        r.updatedAt = p.getUpdatedAt();
        return r;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getPosition() { return position; }
    public String getPositionDesc() { return positionDesc; }
    public String getPositionIcon() { return positionIcon; }
    public Integer getCareerYears() { return careerYears; }
    public String getGithubUrl() { return githubUrl; }
    public String getBlogUrl() { return blogUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


}
