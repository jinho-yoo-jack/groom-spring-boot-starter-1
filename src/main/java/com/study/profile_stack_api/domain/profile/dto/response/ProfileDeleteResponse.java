package com.study.profile_stack_api.domain.profile.dto.response;

public class ProfileDeleteResponse {

    private String message;
    private long deletedId;

    public static ProfileDeleteResponse of(long id) {
        ProfileDeleteResponse profileDeleteResponse = new ProfileDeleteResponse();
        profileDeleteResponse.message = "프로필이 성공적으로 삭제되었습니다.";
        profileDeleteResponse.deletedId = id;

        return profileDeleteResponse;
    }

    //Getter

    public String getMessage() {
        return message;
    }

    public long getDeletedId() {
        return deletedId;
    }
}
