package com.study.domain.profile.dto.request;

public class ProfileUpdateRequest {
    private String name;
    private String email;
    private String bio;
    private String position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;

    public ProfileUpdateRequest() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getPosition() { return position; }
    public Integer getCareerYears() { return careerYears; }
    public String getGithubUrl() { return githubUrl; }
    public String getBlogUrl() { return blogUrl; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setBio(String bio) { this.bio = bio; }
    public void setPosition(String position) { this.position = position; }
    public void setCareerYears(Integer careerYears) { this.careerYears = careerYears; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public void setBlogUrl(String blogUrl) { this.blogUrl = blogUrl; }
}
