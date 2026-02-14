package com.study.profile_stack_api.domain.profile.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Profile {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private Position position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updateAd;

    public Profile(Long id, String name, String email, String bio, Position position, Integer careerYears,
                   String githubUrl, String blogUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.position = position;
        this.careerYears = careerYears;
        this.githubUrl = githubUrl;
        this.blogUrl = blogUrl;
        this.createdAt = LocalDateTime.now();
        this.updateAd = LocalDateTime.now();
    }
}
