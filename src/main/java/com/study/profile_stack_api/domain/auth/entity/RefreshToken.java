package com.study.profile_stack_api.domain.auth.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Refresh 토큰 Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class RefreshToken {
    private Long id;
    private Long memberId;
    private String token;
    private LocalDateTime expiryDate;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
