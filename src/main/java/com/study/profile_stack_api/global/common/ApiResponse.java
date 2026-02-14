package com.study.profile_stack_api.global.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 공통 API 응답 래퍼 클래스
 * 모든 API 응답을 동일한 형식으로 내리기 위해 사용하는 Response 객체
 * 제네릭 타입 <T>를 사용해 어떤 타입의 데이터든 담을 수 있도록 설계
 */
@JsonPropertyOrder({"success", "data", "error"})    // JSON 응답 필드 순서 고정
public class ApiResponse<T> {
    /** 요청 성공 여부 */
    private boolean success;
    /** 성공 시 반환 데이터 */
    private T data;
    /** 실패 시 반환 에러 정보 */
    private ErrorInfo error;

    /**
     * 외부에서 생성하지 못하도록 기본 생성자 private 제한
     * success() 또는 error()로만 생성 가능
     */
    private ApiResponse() {}

    /**
     * 성공 응답 생성 메서드
     *
     * @param data 실제 반환 데이터
     * @return 성공 상태의 ApiResponse 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.data = data;
        response.error = null;
        return response;
    }

    /**
     * 실패 응답 생성 메서드
     *
     * @param code 에러 코드
     * @param error 에러 메시지
     * @return 실패 상태의 ApiResponse 객체
     */
    public static <T> ApiResponse<T> error(String code, String error) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.data = null;
        response.error = new ErrorInfo(code, error);
        return response;
    }

    /**
     * 에러 정보를 담는 내부 Record 클래스
     *
     * @param code 에러 코드
     * @param message 사용자에게 보여줄 에러 메시지
     */
    private record ErrorInfo(String code, String message) {}
}
