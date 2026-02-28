package com.study.profile_stack_api.domain.profile.dto.response;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProfileResponse {

    private long id;
    private String name;
    private String email;
    private String bio;
    private String position;
    private String icon;
    private int careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
