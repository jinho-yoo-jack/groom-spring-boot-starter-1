package com.study.profile_stack_api.domain.profile.entity;

import java.time.LocalDateTime;

public class Profile {

    private long id;
    private String name;
    private String email;
    private String bio;
    private Position position;
    private int career_years;
    private String github_url;
    private String blog_url;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Profile() {}

    public Profile(long id, String name, String email, String bio, Position position, int career_years, String github_url, String blog_url, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.position = position;
        this.career_years = career_years;
        this.github_url = github_url;
        this.blog_url = blog_url;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
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

    public int getCareer_years() {
        return career_years;
    }

    public String getGithub_url() {
        return github_url;
    }

    public String getBlog_url() {
        return blog_url;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
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

    public void setCareer_years(int career_years) {
        this.career_years = career_years;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public void setBlog_url(String blog_url) {
        this.blog_url = blog_url;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
