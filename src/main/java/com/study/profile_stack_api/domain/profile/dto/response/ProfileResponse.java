package com.study.profile_stack_api.domain.profile.dto.response;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String positionName;
    private String positionIcon;
    private int careerYears;
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
        response.positionName = profile.getPosition().name();
        response.positionIcon = profile.getPosition().getIcon();
        response.careerYears = profile.getCareerYears();
        response.githubUrl = profile.getGithubUrl();
        response.blogUrl = profile.getBlogUrl();
        response.createdAt = profile.getCreatedAt();
        response.updatedAt = profile.getUpdatedAt();

        return response;
    }

}
