package com.study.profile_stack_api.auth.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private Long id;
    private String username;
    private String password;
    private MemberRole role;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}