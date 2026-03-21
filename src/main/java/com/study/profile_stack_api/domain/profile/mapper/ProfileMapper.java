package com.study.profile_stack_api.domain.profile.mapper;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    @Mapping(target = "positionName", expression = "java(profile.getPosition().name())")
    @Mapping(target = "positionIcon", expression = "java(profile.getPosition().getIcon())")
    ProfileResponse toResponse(Profile profile);

    List<ProfileResponse> toResponseList(List<Profile> profiles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "position", expression = "java(com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition().toUpperCase()))")
    Profile toEntity(ProfileCreateRequest request);

    @Mapping(target = "position", expression = "java(request.getPosition() != null ? com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition()) : profile.getPosition())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ProfileUpdateRequest request, @MappingTarget Profile profile);
}
