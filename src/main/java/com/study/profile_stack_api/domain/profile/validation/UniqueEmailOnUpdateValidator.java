package com.study.profile_stack_api.domain.profile.validation;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.stereotype.Component;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class UniqueEmailOnUpdateValidator implements ConstraintValidator<UniqueEmailOnUpdate, Object[]> {

    private final ProfileDao profileDao;

    public UniqueEmailOnUpdateValidator(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public boolean isValid(Object[] values, ConstraintValidatorContext context) {
        if (values == null || values.length < 2) {
            return true;
        }

        if (!(values[0] instanceof Long id) || !(values[1] instanceof ProfileUpdateRequest request)) {
            return true;
        }

        String email = request.getEmail();
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        return !profileDao.existsByEmailAndIdNot(id, email);
    }
}
