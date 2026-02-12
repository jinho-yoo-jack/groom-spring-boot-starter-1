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
}
