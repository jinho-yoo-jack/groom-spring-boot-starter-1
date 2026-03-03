package com.study.profile_stack_api.global.exception;

import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler = "에러 나면 어떻게 메시지 보여줄지, 한 곳에서 처리"하는 클래스.
 *
 * [전체 흐름] (예: 프로필 999번 조회했는데 없을 때)
 * 1. 클라이언트가 GET /api/v1/profiles/999 요청
 * 2. Controller → Service → Repository 호출
 * 3. Service(또는 Repository)에서 "999번 없음" → throw new ProfileNotFoundException();
 * 4. 예외가 위로 전파 → Controller 거쳐서 스프링까지 전달됨
 * 5. 스프링: "이 예외가 BusinessException(또는 그 자식)이네 → handleBusinessException 호출"
 *    (어떻게 아나? → ProfileNotFoundException 클래스에 "extends BusinessException" 적혀 있음. 자식 타입은 부모 타입으로도 취급되니까, 터진 예외가 BusinessException 계열인지 한 번에 판단 가능.)
 * 6. handleBusinessException 실행 → e.getErrorCode() 로 PROFILE_NOT_FOUND 꺼냄 → 404 + "프로필을 찾을 수 없습니다." 로 ResponseEntity 만듦 → return
 * 7. 스프링이 그 응답을 클라이언트에게 전달 → 클라이언트는 404 + { success: false, message: "프로필을 찾을 수 없습니다." } 받음
 *
 * [이 파일이 없으면?]
 * - Service에서 throw 해도 "누가 잡아서 404 + 메시지로 응답해?"가 없음. 이 파일이 있으면 스프링이 여기 메서드를 호출해서 응답 만듦.
 *
 * [왜 "Global"?]
 * - Controller가 여러 개여도, 그 안에서 터진 예외가 "전부 여기로" 모임. 그래서 전역(Global) 예외 처리.
 *
 * [두 개의 메서드가 있는 이유]
 * - handleBusinessException: "우리가 만든 예외"(프로필 없음, 이메일 중복 등) → ErrorCode 보고 404/409 + 메시지.
 * - handleException: 그 외 예외(NullPointerException 등) → 500 + "서버 오류가 발생했습니다."
 * - 스프링은 예외 타입에 맞는 @ExceptionHandler를 찾음. BusinessException이면 첫 번째, 나머지는 두 번째.
 */
@RestControllerAdvice
// ↑ "컨트롤러에서 나온 예외는 여기로 모인다"고 스프링에 알리는 표시. 이걸 붙여야 스프링이 이 클래스를 예외 처리용으로 씀.

public class GlobalExceptionHandler {

    /**
     * [이 메서드가 하는 일]
     * - BusinessException (그리고 그 자식: ProfileNotFoundException, TechStackNotFoundException, DuplicateEmailException)이
     *   터지면 "이 메서드가 처리한다"고 선언한 것.
     *
     * [한 줄씩]
     * - @ExceptionHandler(BusinessException.class) = "BusinessException 타입(및 자식) 예외가 터지면 이 메서드를 실행해라"
     * - (BusinessException e) = 터진 예외 객체가 e로 들어옴. 그래서 e.getErrorCode() 로 "어떤 에러인지" 꺼낼 수 있음.
     * - ResponseEntity = "HTTP 응답"을 담는 상자. status(404) + body(응답 본문) 넣어서 돌려주면, 그게 클라이언트에게 전달됨.
     * - code.getStatus() = 404, 409, 500 등 (ErrorCode에 저장된 값)
     * - ApiResponse.error(code.getMessage()) = { success: false, message: "프로필을 찾을 수 없습니다." } 같은 형식
     */
    @ExceptionHandler(BusinessException.class) // "이 타입 예외가 터지면 이 메서드가 처리한다"는 표시.
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode code = e.getErrorCode();   // 예외가 들고 있는 에러 종류 꺼냄 (PROFILE_NOT_FOUND 등)
        return ResponseEntity
                .status(code.getStatus())   // HTTP 상태 코드 (404, 409, 500)
                .body(ApiResponse.error(code.getMessage()));  // 응답 본문 = { success: false, message: "..." }
    }

    /**
     * [이 메서드가 하는 일]
     * - 위 handleBusinessException에서 안 잡힌 "그 외 모든 예외"를 여기서 처리.
     * - 예: NullPointerException, 우리가 안 만든 다른 Exception 등 → 500 + "서버 오류가 발생했습니다."
     *
     * [왜 두 번째로 둠?]
     * - Exception은 모든 예외의 부모. 스프링은 "더 구체적인 타입"을 먼저 찾음.
     * - BusinessException이 터지면 → handleBusinessException이 처리.
     * - 그 외 예외가 터지면 → handleException이 처리.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getStatus())   // 500
                .body(ApiResponse.error(ErrorCode.INTERNAL_ERROR.getMessage()));  // "서버 오류가 발생했습니다."
    }
}
