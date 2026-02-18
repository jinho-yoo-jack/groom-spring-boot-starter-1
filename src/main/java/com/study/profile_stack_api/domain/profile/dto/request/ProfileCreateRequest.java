package com.study.profile_stack_api.domain.profile.dto.request;

public class ProfileCreateRequest {
    private String name;
    private String email;
    private String bio;
    private String position;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;

    public ProfileCreateRequest() {}

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

    public String getGithubUrl() {
        return githubUrl;
    }

    public int getCareerYears() {
        return careerYears;
    }

    public String getBlogUrl() {
        return blogUrl;
    }
}
