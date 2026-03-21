package com.study.profile_stack_api.domain.tech_stack.mapper;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechStackMapper {
    // Entity to DTO
    @Mapping(target = "categoryName", expression = "java(techStack.getCategory().name())")
    @Mapping(target = "categoryIcon", expression = "java(techStack.getCategory().getIcon())")
    @Mapping(target = "proficiencyName", expression = "java(techStack.getProficiency().name())")
    @Mapping(target = "proficiencyIcon", expression = "java(techStack.getProficiency().getIcon())")
    TechStackResponse toResponse(TechStack techStack);

    List<TechStackResponse> toResponseList(List<TechStack> techStacks);

    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", source = "profile")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", expression = "java(com.study.profile_stack_api.domain.tech_stack.entity.Category.valueOf(request.getCategory().toUpperCase()))")
    @Mapping(target = "proficiency", expression = "java(com.study.profile_stack_api.domain.tech_stack.entity.Proficiency.valueOf(request.getProficiency().toUpperCase()))")
    @Mapping(target = "name", source = "request.name")
    TechStack toEntity(TechStackCreateRequest request, Profile profile);

    @Mapping(target = "category", expression = "java(request.getCategory() != null ? com.study.profile_stack_api.domain.tech_stack.entity.Category.valueOf(request.getCategory()) : techStack.getCategory())")
    @Mapping(target = "proficiency", expression = "java(request.getProficiency() != null ? com.study.profile_stack_api.domain.tech_stack.entity.Proficiency.valueOf(request.getProficiency()) : techStack.getProficiency())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(TechStackUpdateRequest request, @MappingTarget TechStack techStack);

}
