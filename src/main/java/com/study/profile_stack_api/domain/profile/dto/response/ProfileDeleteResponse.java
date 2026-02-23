package com.study.profile_stack_api.domain.profile.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDeleteResponse {
    private Long id;
    private String message;

    public static ProfileDeleteResponse of(Long id) {
        ProfileDeleteResponse response = new ProfileDeleteResponse();
        response.setId(id);
        response.setMessage("프로필이 성공적으로 삭제되었습니다.");
        return response;
    }
}
