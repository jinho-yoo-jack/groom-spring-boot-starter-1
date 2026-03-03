package com.study.profile_stack_api.global.exception;

/**
 * 기술 스택을 찾을 수 없을 때 던지는 예외.
 * (예: profileId=1, techStack id=99 조회했는데 없음 → Service에서 throw new TechStackNotFoundException();)
 *
 * - 부모가 BusinessException → errorCode = TECH_STACK_NOT_FOUND (404 + "기술 스택을 찾을 수 없습니다.")
 */
public class TechStackNotFoundException extends BusinessException {

    public TechStackNotFoundException() {
        super(ErrorCode.TECH_STACK_NOT_FOUND);
    }
}
