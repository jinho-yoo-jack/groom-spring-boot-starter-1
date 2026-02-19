package com.study.profile_stack_api.domain.profile.dto.response;

import com.study.profile_stack_api.domain.profile.entity.Profile;

import java.time.LocalDateTime;

public class ProfileResponse {

    private long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private String icon;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProfileResponse() {}

    public static ProfileResponse from(Profile profile) {

        ProfileResponse response = new ProfileResponse();
        response.id = profile.getId();
        response.name = profile.getName();
        response.email = profile.getEmail();
        response.bio = profile.getBio();
        response.position = profile.getPosition().getDescription();
        response.icon = profile.getPosition().getIcon();
        response.careerYears = profile.getCareerYears();
        response.githubUrl = profile.getGithubUrl();
        response.blogUrl = profile.getBlogUrl();
        response.createdAt = profile.getCreatedAt();
        response.updatedAt = profile.getUpdatedAt();

        return response;
    }

    // Getter

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getPosition() {
        return position;
    }

    public String getIcon() {
        return icon;
    }

    public int getCareerYears() {
        return careerYears;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
