package com.study.profile_stack_api.domain.profile.entity;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public class Profile {

    private long id;
    private String name;                // 필수값
    private String email;               // 필수값
    private String bio;                 // null 허용
    private Position position;          // 필수값
    private int careerYears;            // 필수값
    private String githubUrl;           // null 허용
    private String blogUrl;             // null 허용
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Profile() {}

    public Profile(ProfileRequest profileRequest) {
        Optional.ofNullable(profileRequest.getName())
                .ifPresentOrElse(name -> {
                            if (name.length() > 50 || name.trim().isEmpty()) {
                                throw new IllegalArgumentException("이름은 1자~50자이여야 합니다.");
                            }
                            this.setName(name);
                        }, () -> {
                            throw new IllegalArgumentException("이름은 필수입니다.");
                        }
                );
        Optional.ofNullable(profileRequest.getEmail())
                .ifPresentOrElse(email -> {
                            if (email.length() > 100 || email.trim().isEmpty()) {
                                throw new IllegalArgumentException("email은 1자~100자이여야 합니다.");
                            }
                            this.setEmail(email);
                        }, () -> {
                            throw new IllegalArgumentException("email은 필수입니다.");}
                );
        Optional.ofNullable(profileRequest.getBio())
                .ifPresent(bio -> {
                    if (bio.length() > 500 || bio.trim().isEmpty()) {
                        throw new IllegalArgumentException("자기소개는 500자이내여야 합니다.");
                    }
                    this.setBio(bio);
                });
        Optional.ofNullable(profileRequest.getPosition())
                .map(String::toUpperCase)
                .map(Position::valueOf)
                .ifPresentOrElse(
                        this::setPosition,
                        () -> {throw new IllegalArgumentException("존재하지않는 직무 또는 필수입니다.");});
        Optional.ofNullable(profileRequest.getCareerYears())
                .ifPresent(careerYears -> {
                    if (careerYears < 0) {
                        throw new IllegalArgumentException("경력연차는 0이상이여야 합니다.");
                    }
                    this.setCareerYears(careerYears);
                });
        Optional.ofNullable(profileRequest.getGithubUrl())
                .ifPresent( githubUrl -> {
                    if (githubUrl.length() > 200 || githubUrl.trim().isEmpty()) {
                        throw new IllegalArgumentException("github url은 200자 이내여야 합니다.");
                    }
                    this.setGithubUrl(githubUrl);
                });
        Optional.ofNullable(profileRequest.getBlogUrl())
                .ifPresent(blogUrl -> {
                    if (blogUrl.length() > 200 || blogUrl.trim().isEmpty()) {
                        throw new IllegalArgumentException("blog url은 200자 이내여야 합니다.");
                    }
                    this.setBlogUrl(blogUrl);
                });

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Profile update(ProfileRequest profileRequest) {
        Optional.ofNullable(profileRequest.getName())
                .ifPresent( name -> {
                    if (name.length() > 50 || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("이름은 1자~50자이여야 합니다.");
                    }
                    this.setName(name);
                });
        Optional.ofNullable(profileRequest.getEmail())
                .ifPresent(email -> {
                    if (email.length() > 100 || email.trim().isEmpty()) {
                        throw new IllegalArgumentException("email은 1자~100자이여야 합니다.");
                    }
                    this.setEmail(email);
                });
        Optional.ofNullable(profileRequest.getBio())
                .ifPresent(bio -> {
                    if (bio.length() > 500 || bio.trim().isEmpty()) {
                        throw new IllegalArgumentException("자기소개는 500자이내여야 합니다.");
                    }
                    this.setBio(bio);
                });
        Optional.ofNullable(profileRequest.getPosition())
                .map(String::toUpperCase)
                .map(Position::valueOf)
                .ifPresent(this::setPosition);
        Optional.ofNullable(profileRequest.getCareerYears())
                .ifPresent(careerYears -> {
                    if (careerYears < 0) {
                        throw new IllegalArgumentException("경력연차는 0이상이여야 합니다.");
                    }
                    this.setCareerYears(careerYears);
                });
        Optional.ofNullable(profileRequest.getGithubUrl())
                .ifPresent( githubUrl -> {
                    if (githubUrl.length() > 200 || githubUrl.trim().isEmpty()) {
                        throw new IllegalArgumentException("github url은 200자 이내여야 합니다.");
                    }
                    this.setGithubUrl(githubUrl);
                });
        Optional.ofNullable(profileRequest.getBlogUrl())
                .ifPresent(blogUrl -> {
                    if (blogUrl.length() > 200 || blogUrl.trim().isEmpty()) {
                        throw new IllegalArgumentException("blog url은 200자 이내여야 합니다.");
                    }
                    this.setBlogUrl(blogUrl);
                });

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    // Getter
    public long getId() {
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

    public Position getPosition() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setter

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCareerYears(int careerYears) {
        this.careerYears = careerYears;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
