package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;

/**
 * 기술 카테고리 Enum
 */
@Getter
public enum TechCategory {
    LANGUAGE("📝", "프로그래밍 언어"),
    FRAMEWORK("🏗️", "프레임워크"),
    DATABASE("💾", "데이터베이스"),
    DEVOPS("☁️", "DevOps/인프라"),
    TOOL("🔧", "개발 도구"),
    ETC("📦", "기타");

    private final String icon;
    private final String description;

    TechCategory(String icon, String description) {
        this.icon = icon;
        this.description = description;
    }
}