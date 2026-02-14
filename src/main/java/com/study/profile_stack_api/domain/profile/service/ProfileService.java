package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.global.exception.DuplicateEmailException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    /** 의존성 주입: Repository */
    private final ProfileRepository profileRepository;

    /**
     * 생성자 주입
     */
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // ==================== CREATE ====================

    /**
     * 프로필 생성
     * @param request 생성 요청 DTO
     * @return 생성된 프로필 응담 DTO
     */
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        // 유효성 검증
        validataCreateRequest(request);

        // DTO -> Entity변환
        Profile profile = new Profile(
                null,
                request.getName(),
                request.getEmail(),
                request.getBio(),
                Position.valueOf(request.getPosition()),
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl()
        );

        // 저장
        Profile savedProfile = profileRepository.save(profile);

        // Entity -> Response DTO 변환 후 반환
        return ProfileResponse.from(savedProfile);
    }

    // ==================== VALIDATION ====================

    /**
     * 생성 요청 유효성 검증
     */
    private void validataCreateRequest(ProfileCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        if (request.getEmail().length() > 100) {
            throw new IllegalArgumentException("이메일은 100자를 초과할 수 없습니다.");
        }

        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        if (request.getBio().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }

        if (request.getPosition() == null || request.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("직무는 필수입니다.");
        }

        if (request.getCareerYears() == null || request.getCareerYears() < 0) {
            throw new IllegalArgumentException("경력은 0년 이상이어야 합니다.");
        }
    }
}
