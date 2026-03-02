package com.study.profile_stack_api.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {

    private Long id;
    private String username;
}
