package com.study.profile_stack_api.domain.profile.validation;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailIfPresentValidator implements ConstraintValidator<UniqueEmailIfPresent, String> {

    private final ProfileDao profileDao;

    public UniqueEmailIfPresentValidator(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null이면 수정 안 함
        if (value == null) return true;

        // 공백이면 수정 안 함
        if (value.trim().isEmpty()) return true;

        // 존재 하면 실패
        return !profileDao.existsByEmail(value);
    }
}
