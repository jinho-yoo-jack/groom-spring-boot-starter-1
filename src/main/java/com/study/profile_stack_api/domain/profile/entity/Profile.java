package com.study.profile_stack_api.domain.profile.entity;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Profile {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Profile() {}

    public Profile(Long id, String name, String email, String bio, String position, int careerYears,
                   String githubUrl, String blogUrl, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.position = position;
        this.careerYears = careerYears;
        this.githubUrl = githubUrl;
        this.blogUrl = blogUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getPosition() {
        return position;
    }

    public int getCareerYears() {
        return careerYears;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
