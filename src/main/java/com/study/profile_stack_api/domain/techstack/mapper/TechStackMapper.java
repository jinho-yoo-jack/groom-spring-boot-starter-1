package com.study.profile_stack_api.domain.techstack.mapper;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechStackMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "profileId", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    TechStack toEntity(TechStackCreateRequest request);

    @Mapping(target = "categoryIcon", expression = "java(techStack.getCategory().getIcon())")
    @Mapping(target = "proficiencyIcon", expression = "java(techStack.getProficiency().getIcon())")
    TechStackResponse toResponse(TechStack techStack);

    List<TechStackResponse> toResponseList(List<TechStack> techStacks);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "profileId", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    void updateEntity(TechStackUpdateRequest request, @MappingTarget TechStack techStack);
}
