package com.study.profile_stack_api.global.exception;

import lombok.Getter;

/**
 * BusinessException = "업무 중에 난 에러"를 던질 때 쓰는, ErrorCode를 하나 들고 있는 예외 클래스.
 *
 * [왜 이 파일을 만드나요?] "ErrorCode만 있으면 되는 거 아닌지?"
 * - 아니요. 자바에서 "에러 났다"고 알리려면 반드시 "예외 객체"를 던져야 함. (throw new ???)
 * - ErrorCode는 "에러 종류 표(정보)"일 뿐이라서, throw 할 수 없어요.
 * - 그래서 "던질 수 있는 것(Exception)"이 "에러 종류(ErrorCode)"를 들고 있게 한 것.
 *   → BusinessException = "던질 수 있는 예외" + "그 안에 ErrorCode 하나"
 *
 * [흐름]
 * 1. Service에서 "프로필 없음!" 이러면 → throw new ProfileNotFoundException();
 * 2. ProfileNotFoundException은 부모가 BusinessException이고, 안에 errorCode = PROFILE_NOT_FOUND.
 * 3. 예외가 "위로" 전파됨. ("위" = 나를 호출한 쪽. Repository를 부른 건 Service, Service를 부른 건 Controller.
 *    catch 안 하면 "나를 부른 쪽"으로 예외가 넘어감 → Repository → Service → Controller → 스프링 → GlobalExceptionHandler가 잡음)
 * 4. GlobalExceptionHandler가 잡음 → e.getErrorCode() 로 PROFILE_NOT_FOUND 꺼냄 → 404 + 메시지 응답.
 *
 * [RuntimeException이면]
 * - try-catch 안 해도 됨. 터지면 위로 전파되고, 맨 위(GlobalExceptionHandler)에서 한 번에 잡음.
 */
@Getter  // Handler가 e.getErrorCode() 부를 수 있게 getter 필요
public class BusinessException extends RuntimeException {

    /** 이 예외가 "어떤 에러 종류"인지. Handler가 이걸 보고 404/409 + 메시지 정함. */
    private final ErrorCode errorCode;

    /**
     * 생성자. ErrorCode 하나 받아서 저장하고, 그 메시지를 부모(RuntimeException) 예외 메시지로도 넘김.
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
