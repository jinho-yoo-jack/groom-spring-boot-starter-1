package com.study.profile_stack_api.domain.profile.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Profile {
    private Long id;
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

    public void update(String name, String email, String bio, Position position,
                       Integer careerYears, String githubUrl, String blogUrl) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
        if (bio != null) {
            this.bio = bio;
        }
        if (position != null) {
            this.position = position;
        }
        if (careerYears != null) {
            this.careerYears = careerYears;
        }
        if (githubUrl != null) {
            this.githubUrl = githubUrl;
        }
        if (blogUrl != null) {
            this.blogUrl = blogUrl;
        }

        this.updatedAt = LocalDateTime.now();
    }
}
