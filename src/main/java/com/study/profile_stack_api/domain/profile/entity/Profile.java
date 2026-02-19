package com.study.profile_stack_api.domain.profile.entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter // 모든 필드의 Getter를 자동으로 생성
@Setter // 모든 필드의 Setter를 자동으로 생성 (필요할 때만 쓰는 게 좋음)
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 생성
@Builder // 빌더 패턴으로 객체를 생성할 수 있게 함
public class Profile {

    private Long id;
    private String name;
    private String email;
    private String bio;
    private Position position;
    private Integer careerYears;
    private String githubUrl;
    private String blogUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
