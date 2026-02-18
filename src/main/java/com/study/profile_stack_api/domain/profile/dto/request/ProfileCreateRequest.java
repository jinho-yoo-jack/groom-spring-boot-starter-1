package com.study.profile_stack_api.domain.profile.dto.request;

public record ProfileCreateRequest(
        String name,
        String email,
        String bio,
        String position,
        Integer careerYears,
        String githubUrl,
        String blogUrl
) {}


