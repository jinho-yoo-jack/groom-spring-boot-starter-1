package com.study.profile_stack_api.domain.tech_stack.Facade;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import com.study.profile_stack_api.domain.tech_stack.repository.TechStackRepository;
import com.study.profile_stack_api.domain.tech_stack.service.TechStackService;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TechStackFacade {
    // TODO : Facade 적용하기
    private final ProfileService profileService;
    private final TechStackService techStackService;

    public TechStackResponse createTechStack(TechStackCreateRequest request, Long profileId) {
        // TODO: Valid 적용으로 입력 값에 부적절한 값 검증하기
        Profile profile = profileService.getProfileByIdToEntity(profileId);

        TechStack techStack = techStackService.createTechStack(request, profile);
        return TechStackResponse.from(techStack);
    }

    public TechStackResponse getTechStackById(Long profileId, Long id) {
        TechStack techStack = techStackService.getTechStackById(profileId, id);
        return TechStackResponse.from(techStack);
    }

    public TechStackResponse updateTechStack(Long id, Long profileId, TechStackUpdateRequest request) {
        TechStack techStack = techStackService.updateTechStack(id, profileId, request);

        return TechStackResponse.from(techStack);
    }

    public TechStackDeleteResponse deleteTechStack(Long id, Long profileId) {
        return TechStackDeleteResponse.of(techStackService.deleteTechStack(id, profileId));
    }

    public Page<TechStackResponse> getTechStacksWithPaging(Long profileId, int page, int size) {
        Page<TechStack> techStackPage = techStackService.getTechStacksWithPaging(profileId, page, size);

        List<TechStackResponse> content = techStackPage.getContent().stream()
                .map(TechStackResponse::from)
                .collect(Collectors.toList());

        return new Page<>(content, page, size, techStackPage.getTotalElements());
    }
}
