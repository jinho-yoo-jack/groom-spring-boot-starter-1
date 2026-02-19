package com.study.profile_stack_api.domain.profile.dto;

import com.study.profile_stack_api.domain.profile.entity.Position;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {
    /*
    @NotBlank : 문자열 전용 null, 빈값, 공백 모두 허용 안됨
    @NotEmpty : 문자열, 컬렉션 공백만 허용
    @NotNull : 모든 타입. null 불가능

    @Size : 글자 수나 리스트 항목 개수를 제한
    @Min / Max : 숫자의 크기를 제한
     */

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 500)
    private String bio;

    @NotNull(message = "직무는 필수입니다.")
    private String position;

    @NotNull(message = "경력 연차는 필수입니다.")
    @PositiveOrZero(message = "경력은 0년 이상이어야 합니다.")
    private Integer careerYears;

    @Size(max = 200)
    private String githubUrl;

    @Size(max = 200)
    private String blogUrl;
}
