package com.study.profile_stack_api.domain.profile.entity;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    private long id;
    private long memberId;
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

    public void updateName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateBio(String bio) {
        this.bio = bio;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePosition(Position position) {
        this.position = position;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCareerYears(Integer careerYears) {
        this.careerYears = careerYears;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
        this.updatedAt = LocalDateTime.now();
    }

}
