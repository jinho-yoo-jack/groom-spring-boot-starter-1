package com.study.profile_stack_api.domain.tech_stack.service;

import com.study.profile_stack_api.domain.tech_stack.dao.TechStackDao;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechStackService {
    private final TechStackDao techStackDao;

    // Create
    public TechStackResponse createTechStack(TechStackCreateRequest request, Long profileId) {
        validationCreateRequest(request, profileId);

        TechStack techStack = TechStack.builder()
                .id(null)
                .profileId(profileId)
                .name(request.getName())
                .category(Category.valueOf(request.getCategory().toUpperCase()))
                .proficiency(Proficiency.valueOf(request.getProficiency().toUpperCase()))
                .yearsOfExp(request.getYearsOfExp())
                .build();

        TechStack saveTechStack = techStackDao.save(techStack);

        return TechStackResponse.from(saveTechStack);
    }

    // Read
    public List<TechStackResponse> getAllTechStacks(Long profileId) {
        List<TechStack> techStacks = techStackDao.findAll(profileId);

        return techStacks.stream()
                .map(TechStackResponse::from)
                .collect(Collectors.toList());
    }

    public TechStackResponse getTechStackById(Long profileId, Long id) {
        TechStack techStack = techStackDao.findTechStackById(profileId, id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 번호 혹은 ID : " + profileId + ", " + id));

        return TechStackResponse.from(techStack);
    }

    // Update
    public TechStack updateTechStack(Long id, TechStackUpdateRequest request) {
        return null;
    }

    // Validation
    public void validationCreateRequest(TechStackCreateRequest request, Long profileId) {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("올바른 프로필 ID를 입력하세요");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다");
        }
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 공백일 수 없습니다");
        }
        if (request.getProficiency() == null || request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 공백일 수 없습니다");
        }
        if (request.getYearsOfExp() < 0 || request.getYearsOfExp() > 100) {
            throw new IllegalArgumentException("올바른 경험 연수를 입력하세요");
        }

    }

}
