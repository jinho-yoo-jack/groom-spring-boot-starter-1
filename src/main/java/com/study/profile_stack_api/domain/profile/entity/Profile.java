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
    private String name;
    private String email;
    private String bio;
    private Position position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * 프로필 정보 수정
     * Null이 아닌 값만 업데이트
     */
    public void update(String name, String email, String bio, Position position, Integer careerYears,
                       String githubUrl, String blogUrl) {
        // Null이 아닌 경우에만 업데이트
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
    }
}
