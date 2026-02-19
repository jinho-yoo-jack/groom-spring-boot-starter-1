package com.study.profile_stack_api.domain.profile.dto.response;

import java.time.LocalDateTime;

import com.study.profile_stack_api.domain.profile.entity.Profile;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .email(profile.getEmail())
                .bio(profile.getBio())
                .position(profile.getPosition().name())
                .careerYears(profile.getCareerYears())
                .githubUrl(profile.getGithubUrl())
                .blogUrl(profile.getBlogUrl())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}