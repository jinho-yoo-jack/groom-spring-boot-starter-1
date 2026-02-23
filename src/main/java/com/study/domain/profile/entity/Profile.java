package com.study.domain.profile.entity;

import java.time.LocalDateTime;

public class Profile {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Profile(){};

    public Profile(Long id, String name, String email, String bio, String position,
                   Integer careerYears, String githubUrl, String blogUrl,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    // 부분 업데이트 메서드
    public void update(String name, String email, String bio,
                       String position, Integer careerYears,
                       String githubUrl, String blogUrl) {

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

        // 수정 시간 갱신
        this.updatedAt = LocalDateTime.now();
    }

    // Getter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getPosition() { return position; }
    public Integer getCareerYears() { return careerYears; }
    public String getGithubUrl() { return githubUrl; }
    public String getBlogUrl() { return blogUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setBio(String bio) { this.bio = bio; }
    public void setPosition(String position) { this.position = position; }
    public void setCareerYears(Integer careerYears) { this.careerYears = careerYears; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public void setBlogUrl(String blogUrl) { this.blogUrl = blogUrl; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
