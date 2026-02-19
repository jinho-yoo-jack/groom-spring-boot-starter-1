package com.study.profile_stack_api.domain.techstack.dto.response;

import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;

public class TechStackDeleteResponse {

    private String message;
    private long deletedId;

    public static TechStackDeleteResponse of(long id) {
        TechStackDeleteResponse TechStackDeleteResponse = new TechStackDeleteResponse();
        TechStackDeleteResponse.message = "기술 스택이 성공적으로 삭제되었습니다.";
        TechStackDeleteResponse.deletedId = id;

        return TechStackDeleteResponse;
    }

    // Getter

    public String getMessage() {
        return message;
    }

    public long getDeletedId() {
        return deletedId;
    }
}
