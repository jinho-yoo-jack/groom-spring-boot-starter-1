package com.study.profile_stack_api.domain.tech_stack.mapper;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T17:18:21+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.18 (Microsoft)"
)
@Component
public class TechStackMapperImpl implements TechStackMapper {

    @Override
    public TechStackResponse toResponse(TechStack techStack) {
        if ( techStack == null ) {
            return null;
        }

        TechStackResponse.TechStackResponseBuilder techStackResponse = TechStackResponse.builder();

        techStackResponse.id( techStack.getId() );
        techStackResponse.name( techStack.getName() );
        techStackResponse.yearsOfExp( techStack.getYearsOfExp() );
        techStackResponse.createdAt( techStack.getCreatedAt() );
        techStackResponse.updatedAt( techStack.getUpdatedAt() );

        techStackResponse.categoryName( techStack.getCategory().name() );
        techStackResponse.categoryIcon( techStack.getCategory().getIcon() );
        techStackResponse.proficiencyName( techStack.getProficiency().name() );
        techStackResponse.proficiencyIcon( techStack.getProficiency().getIcon() );

        return techStackResponse.build();
    }

    @Override
    public List<TechStackResponse> toResponseList(List<TechStack> techStacks) {
        if ( techStacks == null ) {
            return null;
        }

        List<TechStackResponse> list = new ArrayList<TechStackResponse>( techStacks.size() );
        for ( TechStack techStack : techStacks ) {
            list.add( toResponse( techStack ) );
        }

        return list;
    }

    @Override
    public TechStack toEntity(TechStackCreateRequest request, Profile profile) {
        if ( request == null && profile == null ) {
            return null;
        }

        TechStack.TechStackBuilder techStack = TechStack.builder();

        if ( request != null ) {
            techStack.name( request.getName() );
            techStack.yearsOfExp( request.getYearsOfExp() );
        }
        if ( profile != null ) {
            techStack.profile( profile );
            techStack.updatedAt( profile.getUpdatedAt() );
            techStack.version( profile.getVersion() );
        }
        techStack.category( com.study.profile_stack_api.domain.tech_stack.entity.Category.valueOf(request.getCategory().toUpperCase()) );
        techStack.proficiency( com.study.profile_stack_api.domain.tech_stack.entity.Proficiency.valueOf(request.getProficiency().toUpperCase()) );

        return techStack.build();
    }

    @Override
    public void updateEntityFromRequest(TechStackUpdateRequest request, TechStack techStack) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            techStack.setName( request.getName() );
        }
        if ( request.getYearsOfExp() != null ) {
            techStack.setYearsOfExp( request.getYearsOfExp() );
        }

        techStack.setCategory( request.getCategory() != null ? com.study.profile_stack_api.domain.tech_stack.entity.Category.valueOf(request.getCategory()) : techStack.getCategory() );
        techStack.setProficiency( request.getProficiency() != null ? com.study.profile_stack_api.domain.tech_stack.entity.Proficiency.valueOf(request.getProficiency()) : techStack.getProficiency() );
    }
}
