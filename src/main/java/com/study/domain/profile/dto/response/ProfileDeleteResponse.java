package com.study.domain.profile.dto.response;

public class ProfileDeleteResponse {

    private final String message;
    private final Long deletedId;

    private ProfileDeleteResponse(Builder builder) {
        this.message = builder.message;
        this.deletedId = builder.deletedId;
    }

    public String getMessage() { return message; }
    public Long getDeletedId() { return deletedId; }

    public static Builder builder() {
        return new Builder();
    }

    // 네가 원하는 스타일: of(id)
    public static ProfileDeleteResponse of(Long id) {
        return ProfileDeleteResponse.builder()
                .message("프로필이 정상적으로 삭제되었습니다.")
                .deletedId(id)
                .build();
    }

    public static class Builder {
        private String message;
        private Long deletedId;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder deletedId(Long deletedId) {
            this.deletedId = deletedId;
            return this;
        }

        public ProfileDeleteResponse build() {
            return new ProfileDeleteResponse(this);
        }
    }
}