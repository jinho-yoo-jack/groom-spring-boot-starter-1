package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.domain.techstack.repository.TechStackRepository;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 기술 스택 서비스
 */
@Service
public class TechStackService {
    /** 의존성 주입: Repository */
    private final TechStackRepository techStackRepository;
    private final ProfileRepository profileRepository;

    /**
     * 생성자 주입
     */
    public TechStackService(TechStackRepository techStackRepository, ProfileRepository profileRepository) {
        this.techStackRepository = techStackRepository;
        this.profileRepository = profileRepository;
    }

    // ==================== CREATE ====================

    /**
     * 기술 스택 생성
     *
     * @param request 생성 요청 DTO
     * @return 생성된 기술 스택 응담 DTO
     */
    public TechStackResponse createTechStack(Long profileId, TechStackCreateRequest request) {
        // 유효성 검증
        validataCreateRequest(request);

        // FK 검증
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        // DTO -> Entity변환
        TechStack techStack = new TechStack(
                null,
                profile.getId(),
                request.getName(),
                TechCategory.valueOf(request.getCategory()),
                Proficiency.valueOf(request.getProficiency()),
                request.getYearsOfExp()
        );

        // 저장
        TechStack savedTechStack = techStackRepository.save(techStack);

        // Entity -> Response DTO 변환 후 반환
        return TechStackResponse.from(savedTechStack);
    }

    // ==================== VALIDATION ====================

    /**
     * 생성 요청 유효성 검증
     */
    private void validataCreateRequest(TechStackCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("기술명은 필수입니다.");
        }

        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("기술명은 50자를 초과할 수 없습니다.");
        }

        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("기술 카테고리는 필수입니다.");
        }

        if (request.getProficiency() == null || request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 필수입니다.");
        }

        if (request.getYearsOfExp() == null || request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("사용 경험은 0년 이상이어야 합니다.");
        }
    }
}
