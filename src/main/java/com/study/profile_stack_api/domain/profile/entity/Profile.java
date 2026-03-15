package com.study.profile_stack_api.domain.profile.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 프로필 Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    private Long id;
    private Long memberId;
    private String name;
    private String email;
    private String bio;
    private Position position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
