package com.study.profile_stack_api.domain.profile.dto.response;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class ProfileResponse {
    Long id;
    String name;
    String email;
    String bio;
    String positionName;
    String positionIcon;
    int careerYears;
    String githubUrl;
    String blogUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .email(profile.getEmail())
                .bio(profile.getBio())
                .positionName(profile.getPosition().name())
                .positionIcon(profile.getPosition().getIcon())
                .careerYears(profile.getCareerYears())
                .githubUrl(profile.getGithubUrl())
                .blogUrl(profile.getBlogUrl())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

}
