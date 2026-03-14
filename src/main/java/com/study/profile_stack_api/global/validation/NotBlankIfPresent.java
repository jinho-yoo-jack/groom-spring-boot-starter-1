package com.study.profile_stack_api.global.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 값이 존재하는 경우 공백 문자열 입력을 허용하지 않는 검증 어노테이션
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
public @interface NotBlankIfPresent {
    String message() default "빈 값일 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
