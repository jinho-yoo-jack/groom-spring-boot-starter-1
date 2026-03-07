package com.study.profile_stack_api.domain.tech_stack.service;

import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.domain.tech_stack.dao.TechStackDao;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public TechStackResponse getTechStackById(Long profileId ,Long id) {
        TechStack techStack = techStackDao.findTechStackById(profileId ,id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로필 번호 혹은 ID : " + profileId + ", " + id));

        return TechStackResponse.from(techStack);
    }

    // Update
    public TechStackResponse updateTechStack(Long id, Long profileId ,TechStackUpdateRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        TechStack techStack = techStackDao.findTechStackById(profileId ,id)
                .orElseThrow(() -> new IllegalArgumentException("해당 Tech Stack을 찾을 수 없습니다. ID: " + id));

        if (request.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 존재하지 않습니다");
        }

        validationUpdateRequest(request, profileId);

        Category category = null;
        Proficiency proficiency = null;

        if (request.getCategory() != null) {
            try {
                category = Category.valueOf(request.getCategory().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 카테고리입니다");
            }
        }

        if (request.getProficiency() != null) {
            try {
                proficiency = Proficiency.valueOf(request.getProficiency().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 숙련도입니다");
            }
        }

        techStack.update(
                request.getName(),
                category,
                proficiency,
                request.getYearsOfExp()
        );

        TechStack updatedTechStack = techStackDao.update(techStack);
        return TechStackResponse.from(updatedTechStack);
    }

    // Delete
    public TechStackDeleteResponse deleteTechStack(Long id, Long profileId) {
        if (!techStackDao.exitsById(id)) {
            throw new ResourceNotFoundException(id);
        }

        techStackDao.deleteById(id);

        return TechStackDeleteResponse.of(id);
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
