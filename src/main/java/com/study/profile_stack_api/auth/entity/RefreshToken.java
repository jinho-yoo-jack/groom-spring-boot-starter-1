package com.study.profile_stack_api.auth.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    private long id;
    private long member_id;
    private String token;
    private LocalDateTime expiry_date;

    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();
}
