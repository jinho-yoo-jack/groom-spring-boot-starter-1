package com.study.profile_stack_api.global.exception;

import lombok.Getter; 
// Lombok = 반복해서 쓰는 코드를 줄여 주는 라이브러리. 우리가 직접 안 써도, 컴파일할 때 그 코드가 자동으로 들어간다.
// @Getter 를 클래스(또는 enum) 위에 붙이면→ 그 안에 있는 필드마다 getter 메서드를 자동 생성해줌.
/*
 * [Lombok 어노테이션 정리]
 * 어노테이션          | Lombok이 하는 일
 * ------------------|--------------------------------------------------------
 * @Getter           | 모든 필드에 대해 get필드명() 메서드 자동 생성
 * @Setter           | 모든 필드에 대해 set필드명(값) 메서드 자동 생성
 * @Data             | Getter + Setter + toString + equals + hashCode 등 자동 생성 (DTO에서 자주 씀)
 * @NoArgsConstructor| 인자 없는 생성자 자동 생성
 * @AllArgsConstructor| 모든 필드 받는 생성자 자동 생성
 */
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 Enum.
 *
 * Enum = "이름이 정해진 값만 쓸 수 있는 타입". (PROFILE_NOT_FOUND, TECH_STACK_NOT_FOUND ... 이 4개만 존재)
 * 예: "에러 종류"를 무한히 만들 수 있게 두면 관리가 어려우니까, "프로필 없음", "기술스택 없음", "이메일 중복", "서버 오류" 이 4가지만 쓰자고 이름을 고정해 두는 것.
 */
@Getter  // Lombok: 아래 필드(status, message)마다 getter를 "자동으로" 만들어 줌. → getStatus(), getMessage() 가 코드에 안 보이지만 컴파일 시 생김.
public enum ErrorCode {

    // ---------- Enum "값" 4개. 각각 이름 + (HTTP상태, 메시지) 한 쌍 ----------
    // HttpStatus = Spring이 제공하는 타입. (404, 409, 500 등)
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필을 찾을 수 없습니다."),           // "프로필 없음" + 404 + "프로필을 찾을 수 없습니다."
    TECH_STACK_NOT_FOUND(HttpStatus.NOT_FOUND, "기술 스택을 찾을 수 없습니다."),     // "기술스택 없음" + 404 + "기술 스택을 찾을 수 없습니다."
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),            // "이메일 중복" + 409 + "이미 사용 중인 이메일입니다."
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");   // "서버 오류" + 500 + "서버 오류가 발생했습니다."

    // ---------- 각 Enum 값이 "가지고 있을" 필드 ----------
    private final HttpStatus status;   // HTTP 상태 (404, 409, 500 등). Spring이 제공하는 타입.
    private final String message;     // 사용자에게 보여줄 문구.

    // ---------- 생성자: Enum 값 만들 때 (status, message) 넣으면 이 필드에 저장됨 ----------
    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
