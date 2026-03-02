package com.study.profile_stack_api.domain.profile.dto.request;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.global.validation.EnumValid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProfileRequest {

    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @Size(min = 1, max = 500, message = "자기소개는 500자 이하여야 합니다")
    private String bio;

    @NotBlank(message = "직무는 필수입니다")
    @EnumValid(enumClass = Position.class, message = "직무는 BACKEND, FRONTEND, FULLSTACK, MOBILE, DEVOPS, DATA, AI, ETC 중 하나여야 합니다")
    private String position;

    @Min(value = 0, message = "경력연차는 0이상이여야 합니다.")
    private Integer careerYears;

    @Size(min = 1, max = 200, message = "GitHub 주소는 200자 이하여야 합니다")
    private String githubUrl;

    @Size(min = 1, max = 200, message = "블로그 주소는 200자 이하여야 합니다")
    private String blogUrl;

    public boolean hasNoUpdates() {
        return name == null
                && email == null
                && bio == null
                && position == null
                && careerYears == null
                && githubUrl == null
                && blogUrl == null;
    }
}
