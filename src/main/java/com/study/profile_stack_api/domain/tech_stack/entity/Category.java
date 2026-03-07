package com.study.profile_stack_api.domain.tech_stack.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    LANGUAGE("📃", "프로그래밍 언어"),
    FRAMEWORK("🪛", "프레임워크"),
    DATABASE("💾", "데이터베이스"),
    DEVOPS("☁️", "DevOps/인프라"),
    TOOL("🔧", "개발 도구"),
    ETC("🗃️", "기타");

    private final String icon;
    private final String description;
}
