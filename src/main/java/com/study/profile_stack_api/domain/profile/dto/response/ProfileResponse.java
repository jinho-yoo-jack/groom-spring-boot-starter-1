package com.study.profile_stack_api.domain.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 프로필 응답 DTO
 */
@Getter
@JsonPropertyOrder({
        "id", "name", "email",
        "bio", "position", "positionIcon",
        "careerYears", "githubUrl", "blogUrl",
        "createdAt", "updatedAt"
})
public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private String positionIcon;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProfileResponse from(Profile profile) {
        ProfileResponse response = new ProfileResponse();
        response.id = profile.getId();
        response.name = profile.getName();
        response.email = profile.getEmail();
        response.bio = profile.getBio();
        response.position = profile.getPosition().name();
        response.positionIcon = profile.getPosition().getIcon();
        response.careerYears = profile.getCareerYears();
        response.githubUrl = profile.getGithubUrl();
        response.blogUrl = profile.getBlogUrl();
        response.createdAt = profile.getCreatedAt();
        response.updatedAt = profile.getUpdatedAt();
        return response;
    }
}
