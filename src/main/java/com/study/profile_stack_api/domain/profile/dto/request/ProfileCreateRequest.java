package com.study.profile_stack_api.domain.profile.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileCreateRequest {
    private String name;
    private String email;
    private String bio;
    private String position;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;
}
