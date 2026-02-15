package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.global.exception.DuplicateEmailException;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 프로필 서비스
 */
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

    // ==================== READ ====================

    /**
     * 전체 프로필 조회 (최신순 정렬)
     *
     * @return 모든 프로필 응답 DTO 리스트
     */
    public List<ProfileResponse> getAllProfiles() {
        // Repository에서 모든 프로필 조회
        List<Profile> profiles = profileRepository.findAll();

        // Entity 리스트 -> Response DTO 리스트로 변환
        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * ID로 프로필 단건 조회
     *
     * @param id 조회할 프로필 ID
     * @return 프로필 응답 DTO
     */
    public ProfileResponse getProfileById(Long id) {
        // Repository에서 ID로 조회, 존재하지 않으면 예외 처리
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        // Entity -> Response DTO로 변환
        return ProfileResponse.from(profile);
    }

    /**
     * 직무별 프로필 조회
     *
     * @param positionName 조회할 직무 이름
     * @return 직무별 프로필 응답 DTO 리스트
     */
    public List<ProfileResponse> getProfileByPosition(String positionName) {
        // 직무 이름으로 해당 직무 생성, 없는 직무면 예외 처리
        Position position;
        try {
            position = Position.valueOf(positionName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "유효하지 않은 직무입니다: " + positionName
            );
        }

        // Repository에서 직무로 조회
        List<Profile> profiles = profileRepository.findByPosition(position);

        // Entity 리스트 -> Response DTO 리스트로 변환
        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        // 기존 프로필 조회
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        // 수정 내용 있는지 확인
        if (request.hashNoUpdates()) {
            throw new IllegalArgumentException("수정 내용이 없습니다.");
        }

        // 수정값 유효성 검증
        validataUpdateRequest(request);

        // 직무 변환 (Null 아닌 경우에만)
        Position position = null;
        if (request.getPosition() != null) {
            try {
                position = Position.valueOf(request.getPosition().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 직무 입니다.");
            }
        }

        // Entity 업데이트 (Null이 아닌 값만 반영)
        profile.update(
                request.getName(),
                request.getEmail(),
                request.getBio(),
                position,
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl()
        );

        // 저장 및 응답 반환
        Profile updatedProfile = profileRepository.update(profile);
        return ProfileResponse.from(updatedProfile);
    }

    // ==================== DELETE ====================

    /**
     * 프로필 한개를 삭제
     *
     * @param id 삭제할 프로필 ID
     * @return 삭제 결과  응답
     */
    public ProfileDeleteResponse deleteProfile(Long id) {
        // ID에 따른 프로필이 있는지 확인
        if (!profileRepository.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }

        // 삭제 수행
        boolean isDeleted = profileRepository.deleteById(id);

        // 삭제 결과 반환
        return ProfileDeleteResponse.of(id, isDeleted);
    }

    /**
     * 전체 프로필 삭제
     *
     * @return 삭제 결과 응답
     */
    public Map<String, Object> deleteAllProfiles() {
        // 프로필 총 개수 확인
        long deleteCound = profileRepository.deleteAll();

        // 삭제 결과 반환
        return Map.of(
                "message", "전체 프로필이 성공적으로 삭제되었습니다.",
                "deletedCount", deleteCound
        );
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

    public void validataUpdateRequest(ProfileUpdateRequest request) {
        if (request.getName() != null) {
            if (request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
            }
            if (request.getName().length() > 50) {
                throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
            }
        }

        if (request.getEmail() != null) {
            if (request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
            }
            if (request.getEmail().length() > 100) {
                throw new IllegalArgumentException("이메일은 100자를 초과할 수 없습니다.");
            }
            if (profileRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateEmailException(request.getEmail());
            }
        }

        if (request.getBio() != null && request.getBio().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }

        if (request.getPosition() != null && request.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("직무는 빈 값일 수 없습니다.");
        }

        if (request.getCareerYears() != null && request.getCareerYears() < 0) {
            throw new IllegalArgumentException("경력은 0년 이상이어야 합니다.");
        }
    }
}
