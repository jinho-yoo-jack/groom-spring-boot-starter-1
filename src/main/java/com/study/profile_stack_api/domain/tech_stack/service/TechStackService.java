package com.study.profile_stack_api.domain.tech_stack.service;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.domain.tech_stack.dao.TechStackDao;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import com.study.profile_stack_api.domain.tech_stack.mapper.TechStackMapper;
import com.study.profile_stack_api.domain.tech_stack.repository.TechStackRepository;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechStackService {
    private final TechStackDao techStackDao;
    private final TechStackRepository techStackRepository;
    private final TechStackMapper techStackMapper;
    private static final int MAX_PAGE_SIZE = 100;

    // Create
    @Transactional
    public TechStack createTechStack(TechStackCreateRequest request, Profile profile) {
        validationCreateRequest(request, profile.getId());

        TechStack techStack = techStackMapper.toEntity(request, profile);
        techStackRepository.save(techStack);

        return techStack;
    }

    // Read
    public TechStack getTechStackById(Long profileId, Long id) {
        return techStackRepository.findByIdOptimized(profileId, id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 번호 혹은 ID : " + profileId + ", " + id));
    }

    public Page<TechStack> getTechStacksWithPaging(Long profileId, int page, int size) {
        page = Math.max(0, page);
        size = Math.min(Math.max(1, size), MAX_PAGE_SIZE);

        return techStackDao.findAllWithPaging(profileId, page, size);
    }

    // Update
    @Transactional
    public TechStack updateTechStack(Long id, Long profileId ,TechStackUpdateRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        TechStack techStack = techStackRepository.findByIdOptimized(profileId ,id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Tech Stack을 찾을 수 없습니다. ID: " + id));

        if (request.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 존재하지 않습니다");
        }

        validationUpdateRequest(request, profileId);

        techStackMapper.updateEntityFromRequest(request, techStack);

        return techStack;
    }

    // Delete
    public Long deleteTechStack(Long id, Long profileId) {
        if (!techStackRepository.existsByIdAndProfileId(id, profileId)) {
            throw new ResourceNotFoundException(id);
        }

        techStackDao.deleteById(id);

        return id;
    }

    // Validation
    public void validationCreateRequest(TechStackCreateRequest request, Long profileId) {
        if (profileId <= 0) {
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

    private void validationUpdateRequest(TechStackUpdateRequest request, Long profileId) {
        if (profileId <= 0) {
            throw new IllegalArgumentException("올바른 프로필 ID를 입력하세요");
        }
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다");
        }
        if (request.getCategory() != null && request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 공백일 수 없습니다");
        }
        if (request.getProficiency() != null && request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 공백일 수 없습니다");
        }
        if (request.getYearsOfExp() < 0 || request.getYearsOfExp() > 100) {
            throw new IllegalArgumentException("올바른 경험 연수를 입력하세요");
        }
    }

}
