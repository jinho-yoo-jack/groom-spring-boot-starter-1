package com.study.profile_stack_api.global.exception;

/**
 * 프로필을 찾을 수 없을 때 던지는 예외.
 * (예: id=999 조회했는데 DB에 없음 → Service에서 throw new ProfileNotFoundException();)
 *
 * - 부모가 BusinessException → 안에 errorCode = PROFILE_NOT_FOUND (404 + "프로필을 찾을 수 없습니다.")
 * - 생성자에서 super(ErrorCode.PROFILE_NOT_FOUND) 만 넘기면, 나머지(HTTP 상태·메시지)는 ErrorCode가 갖고 있음.
 */
public class ProfileNotFoundException extends BusinessException {

    public ProfileNotFoundException() {
        super(ErrorCode.PROFILE_NOT_FOUND);
    }
}
