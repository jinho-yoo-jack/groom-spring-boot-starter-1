package com.study.profile_stack_api.global.common;

/**
 * 공통 API 응답 래퍼 클래스.
 * API가 클라이언트에게 줄 대답을 항상 같은 형태로 포장하는 클래스이다.
 * 모든 API 응답을 success / data / message 형식으로 통일한다. 3가지 응답만 보냄.
 *
 * 언제 쓰나? → Controller에서 "return ApiResponse.success(데이터);" 할 때.
 *
 * @param <T> 응답 데이터 타입 (예: ProfileResponse, Page<ProfileResponse> 등. 나중에 정함.)
 */

// <T> = "지금은 타입 이름 안 정하고, 나중에 쓸 때 정할 거야" (제네릭).
// return = "이 메서드는 여기서 이 값을 돌려주고 끝난다".
// void = "돌려줄 값 없음" (setter는 그냥 넣기만 하니까 void).

public class ApiResponse<T> {
    // ---------- 필드  ----------
    /** true면 성공, false면 실패(에러) */
    private boolean success;
    /** 실제 응답 내용 데이터. 실패 시에는 null */
    private T data;
    /** 사용자에게 보여줄 메시지. 성공 시 안내 문구, 실패 시 에러 메시지 */
    private String message;

    // ---------- 생성자 ----------
    /**
     * 생성자를 private으로 막아둔 이유:
     * 밖에서 이 칸들을 직접 만들지 못하게 하고, 정해 준 방법(Getter, Setter)으로만 값을 넣을 수 있게 하기 위함.
     */
    private ApiResponse(boolean success, T data, String message) {
        // this. = "이 객체의" 필드. 왼쪽은 필드, 오른쪽은 넘겨받은 값.
        this.success = success;
        this.data = data;
        this.message = message;
    }

    // ---------- 성공 응답 만들기 (정적 팩토리 메서드) ----------
    // static = 객체 안 만들고 클래스 이름으로 바로 부름. 예: ApiResponse.success(데이터);
    /** 성공 + 데이터. 메시지는 "요청이 성공적으로 처리되었습니다." 고정 */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "요청이 성공적으로 처리되었습니다.");  // 상자 새로 만들어서 돌려줌
    }

    /** 성공 + 데이터 + 내가 정한 메시지 */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    /** 성공인데 데이터 없음 (예: 삭제 완료 응답) */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, null, "요청이 성공적으로 처리되었습니다.");
    }

    // ---------- 실패 응답 만들기 ----------

    /** 실패. 에러 메시지만 넣으면 됨. data는 자동으로 null(없음) */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message);  // null = 데이터 없음
    }

    // ---------- Getter ----------
    // 클라이언트가 success / data / message 로 읽을 수 있게 getter 필요

    // success 필드의 값을 "읽어서" 알려줌
    public boolean isSuccess() {
        return success;
    }

    // data 필드를 읽어서 알려줌
    public T getData() {
        return data;
    }

    // message 필드를 읽어서 알려줌
    public String getMessage() {
        return message;
    }

    // ---------- Setter ----------
    // 만든 뒤에 값을 바꿔야 할 때 사용. 보통은 success()/error()로만 만든 뒤엔 안 바꿔도 되고, 필요할 때만 set 사용.
    // success 필드에 값을 "넣어줌"
    public void setSuccess(boolean success) {
        this.success = success;  // this.success = 이 객체의 필드, success = 넘겨받은 값 (이름이 같아서 this로 구분)
    }

    // data 필드에 값을 "넣어줌"
    public void setData(T data) {
        this.data = data;
    }

    // message 필드에 값을 "넣어줌"
    public void setMessage(String message) {
        this.message = message;
    }
}
