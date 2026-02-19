package com.study.profile_stack_api.domain.profile.dto;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static ProfileResponse from(Profile profile) {
        Position position = Position.of(profile.getPosition());

        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .email(profile.getEmail())
                .bio(profile.getBio())
                .position(position.getDescription())
                .positionIcon(position.getIcon())
                .careerYears(profile.getCareerYears())
                .githubUrl(profile.getGithubUrl())
                .blogUrl(profile.getBlogUrl())
                .createdAt(profile.getCreatedAt().toLocalDate())
                .updatedAt(profile.getUpdatedAt().toLocalDate())
                .build();
    }
}