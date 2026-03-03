package com.study.profile_stack_api.global.exception;

/**
 * 이미 사용 중인 이메일로 가입/수정 시도할 때 던지는 예외.
 * (예: 프로필 생성·수정 시 이메일 중복 검사해서 있으면 → throw new DuplicateEmailException();)
 *
 * - 부모가 BusinessException → errorCode = DUPLICATE_EMAIL (409 + "이미 사용 중인 이메일입니다.")
 */
public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
