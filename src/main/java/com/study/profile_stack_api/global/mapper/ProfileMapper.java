package com.study.profile_stack_api.global.mapper;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *  MapStruct Mapper Interface for Profile
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    /**
     * ProfileRequest DTO -> Profile Entity 변환
     *
     * @param request
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "position", expression = "java(com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition()))")
    Profile toEntity(ProfileRequest request);

    /**
     * Profile Entity -> ProfileResponse DTO 변환
     * @param profile
     * @return
     */
    @Mapping(target = "icon", expression = "java(profile.getPosition().getIcon())")
    ProfileResponse toResponse(Profile profile);

    /**
     * ProfileRequest의 값으로 기존 Profile Entity 업데이트
     *
     * @param request
     * @param profile
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "position", expression = "java(request.getPosition() != null ? com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition()) : profile.getPosition())")
    void updateEntityFromRequest(ProfileRequest request, @MappingTarget Profile profile);

    /**
     * 부분 업데이트를 위한 커스텀 메서드
     * null이 아닌 필드만 업데이트
     *
     * @param request 업데이트 요청 DTO
     * @param entity 업데이트할 기존 Entity
     */
    default void partialUpdate(ProfileRequest request, Profile entity) {
        if (request.getName() != null) {
            entity.updateName(request.getName());
        }
        if (request.getEmail() != null) {
            entity.updateEmail(request.getEmail());
        }
        if (request.getBio() != null) {
            entity.updateBio(request.getBio());
        }
        if (request.getPosition() != null) {
            entity.updatePosition(Position.valueOf(request.getPosition()));
        }
        if (request.getCareerYears() != null) {
            entity.updateCareerYears(request.getCareerYears());
        }
        if (request.getGithubUrl() != null) {
            entity.updateGithubUrl(request.getGithubUrl());
        }
        if (request.getBlogUrl() != null) {
            entity.updateBio(request.getBlogUrl());
        }
    }
}
