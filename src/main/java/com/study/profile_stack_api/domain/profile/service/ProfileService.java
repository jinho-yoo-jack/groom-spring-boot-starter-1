package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.dao.ProfileDao;
import com.study.profile_stack_api.global.exception.ApiException;
import com.study.profile_stack_api.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {
    // ProfileDao 인터페이스로 컨트롤
    private final ProfileDao repository;
    // IoC에 의한 종속성 주입
    public ProfileService(ProfileDao profileDao) {this.repository = profileDao;}

    // === Create ===
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        // 1. 리퀘스트 바디 검증
        validateCreateProfile(request);

        // 2. DTO -> Entity 변환
        Profile profile = new Profile(
                null,
                request.getName().trim(),
                request.getEmail().trim(),
                request.getBio().trim(),
                Position.valueOf(request.getPosition()),
                request.getCareerYears(),
                request.getGithubUrl().trim(),
                request.getBlogUrl().trim(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        // 3. 레포지토리 저장
        Profile newProfile = repository.save(profile);

        // 4. Entity -> DTO 변환 후 리턴
        return ProfileResponse.from(newProfile);
    }

    // === Read ===
    public ProfileResponse getProfileById(Long id) {
        Optional<Profile> result = repository.findById(id);
        Profile profile = result.orElseThrow(() -> new RuntimeException("Profile not found"));
        return ProfileResponse.from(profile);
    }

    // === Update ===

    // === Delete ===

    // ===============================================

    // === Validation ===
    private void validateCreateProfile(ProfileCreateRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String bio = request.getBio();
        String position = request.getPosition();
        int careerYears = request.getCareerYears();
        String githubUrl = request.getGithubUrl();
        String blogUrl = request.getBlogUrl();

        // 이름: 필수, 1~50자
        if (name == null || name.trim().isEmpty()) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "이름은 필수 입력입니다.");
        } else if (name.length() > 50) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "이름은 1자 이상, 50자 이하여야 합니다.");
        }

        // 이메일
        if (email == null || email.trim().isEmpty()) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "이메일은 필수입니다.");
        } else if (email.length() > 100) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "이메일은 1자 이상, 100자 이하여야 합니다.");
        }
        if (repository.existsByEmail(email)) {
            throw new ApiException(ErrorCode.DUPLICATE_EMAIL, "이미 존재하는 이메일 주소입니다.");
        }

        // 바이오
        if (bio != null && bio.length() > 500) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "자기소개는 500자 이하여야 합니다.");
        }

        // 직무
        if (position == null || position.trim().isEmpty()) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "직무는 필수입니다.");
        }
        try {
            Position.valueOf(position);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "정의되지 않은 직무 입력입니다.");
        }

        // 경력 연차
        if (careerYears < 0) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "경력 연차는 0 이상이어야 합니다.");
        }

        // 깃허브 링크
        if (githubUrl != null && githubUrl.length() > 200) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "깃허브 링크는 최대 200자여야 합니다.");
        }

        // 블로그 링크
        if (blogUrl != null && blogUrl.length() > 200) {
            throw new ApiException(ErrorCode.INVALID_INPUT, "블로그 링크는 최대 200자여야 합니다.");
        }
    }

}
