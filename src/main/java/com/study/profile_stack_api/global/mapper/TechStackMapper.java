package com.study.profile_stack_api.global.mapper;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *  MapStruct Mapper Interface for techStack
 */
@Mapper(componentModel = "spring")
public interface TechStackMapper {

    /**
     * TechStackRequest DTO -> TechStack Entity 변환
     *
     * @param request
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "techCategory", expression = "java(com.study.profile_stack_api.domain.techstack.entity.TechCategory.valueOf(request.getCategory()))")
    @Mapping(target = "proficiency", expression = "java(com.study.profile_stack_api.domain.techstack.entity.Proficiency.valueOf(request.getProficiency()))")
    TechStack toEntity(TechStackRequest request);

    /**
     * TechStack DTO -> TechStackResponse 변환
     *
     * @param techStack
     * @return
     */
    @Mapping(target = "techIcon", expression = "java(techStack.getTechCategory().getIcon())")
    @Mapping(target = "proficiencyIcon", expression = "java(techStack.getProficiency().getIcon())")
    TechStackResponse toResponse(TechStack techStack);


    /**
     * 부분 업데이트를 위한 커스텀 메서드
     * null이 아닌 필드만 업데이트
     *
     * @param request
     * @param entity
     */
    default void partialUpdate(TechStackUpdateRequest request, TechStack entity) {
        if (request.getName() != null) {
            entity.updateName(request.getName());
        }
        if (request.getCategory() != null) {
            entity.updateTechCategory(TechCategory.valueOf(request.getCategory()));
        }
        if (request.getProficiency() != null) {
            entity.updateProficiency(Proficiency.valueOf(request.getProficiency()));
        }
        if (request.getYearsOfExp() != null) {
            entity.updateYearsOfExp(request.getYearsOfExp());
        }
    }
}
