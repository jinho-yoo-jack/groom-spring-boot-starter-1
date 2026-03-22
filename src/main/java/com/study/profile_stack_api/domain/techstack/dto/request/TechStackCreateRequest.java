package com.study.profile_stack_api.domain.techstack.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기술 스택 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStackCreateRequest {
    @NotBlank(message = "기술명은 필수입니다.")
    @Size(max = 50, message = "기술명은 50자를 초과할 수 없습니다.")
    private String name;

    @NotBlank(message = "기술 카테고리는 필수입니다.")
    private String category;

    @NotBlank(message = "숙련도는 필수입니다.")
    private String proficiency;

    @NotNull(message = "사용 경험은 필수입니다.")
    @Min(value = 0, message = "사용 경험은 0년 이상이어야 합니다.")
    private Integer yearsOfExp;
}
