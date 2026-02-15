package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.domain.techstack.repository.TechStackRepository;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 프로필별 기술 스택 생성
     *
     * @param profileId 생성할 프로필 ID
     * @param request 생성 요청 DTO
     * @return 생성된 기술 스택 응담 DTO
     */
    public TechStackResponse createTechStackByProfileId(Long profileId, TechStackCreateRequest request) {
        // 유효성 검증
        validataCreateRequest(request);

        // FK 검증
        Profile profile = validataProfileId(profileId);

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
        TechStack savedTechStack = techStackRepository.saveByProfileId(techStack);

        // Entity -> Response DTO 변환 후 반환
        return TechStackResponse.from(savedTechStack);
    }

    // ==================== READ ====================

    /**
     * 프로필별 기술 스택 전체 조회 (최신순 정렬)
     *
     * @param profileId 조회할 프로필 ID
     * @return 프로필별 기술 스택 전체 응답 DTO 리스트
     */
    public List<TechStackResponse> getAllTechStacksByProfileId(Long profileId) {
        // FK 검증
        validataProfileId(profileId);

        // Repository에서 profileId에 해당하는 기술 스택만 조회
        List<TechStack> techStacks = techStackRepository.findAllByProfileId(profileId);

        // Entity 리스트 -> Response DTO 리스트로 변환
        return techStacks.stream()
                .map(TechStackResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 프로필별 기술 스택 ID로 단건 조회
     *
     * @param profileId 조회할 프로필 ID
     * @param id 조회할 기술 스택 ID
     * @return 기술 스택 응답 DTO
     */
    public TechStackResponse getTechStackByProfileIdAndId(Long profileId, Long id) {
        // FK 검증
        validataProfileId(profileId);

        // Repository에서 profileId + id로 조회, 존재하지 않으면 예외 처리
        TechStack techStack = techStackRepository.findByProfileIdAndId(profileId, id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        // Entity -> Response DTO로 변환
        return TechStackResponse.from(techStack);
    }

    // ==================== UPDATE ====================

    /**
     * 프로필별 기술 스택 수정
     *
     * @param profileId 수정할 프로필 ID
     * @param id 수정할 기술 스택 ID
     * @param request 수정 요청 데이터
     * @return 수정된 기술 스택 응답
     */
    public TechStackResponse updateTechStackByProfileId(Long profileId, Long id, TechStackUpdateRequest request) {
        Objects.requireNonNull(profileId);
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        // FK 검증
        validataProfileId(profileId);

        // 기존 기술 스택 조회
        TechStack techStack = techStackRepository.findByProfileIdAndId(profileId, id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        // 수정 내용 있는지 확인
        if (request.hashNoUpdates()) {
            throw new IllegalArgumentException("수정 내용이 없습니다.");
        }

        // 수정값 유효성 검증
        validataUpdateRequest(request);

        // 기술 카테고리 및 숙련도 변환 (Null 아닌 경우에만)
        TechCategory category = null;
        Proficiency proficiency = null;
        if (request.getCategory() != null) {
            try {
                category = TechCategory.valueOf(request.getCategory().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 기술 스택 입니다.");
            }
        }

        if (request.getProficiency() != null) {
            try {
                proficiency = Proficiency.valueOf(request.getProficiency().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 숙련도 입니다.");
            }
        }

        // Entity 업데이트 (Null이 아닌 값만 반영)
        techStack.update(
                request.getName(),
                category,
                proficiency,
                request.getYearsOfExp()
        );

        // 저장 및 응답 반환
        TechStack updatedTechStack = techStackRepository.updateByProfileId(techStack);
        return TechStackResponse.from(updatedTechStack);
    }

    // ==================== DELETE ====================

    /**
     * 프로필별 기술 스택 단건 삭제
     *
     * @param profileId 삭제할 프로필 ID
     * @param id 삭제할 기술 스택 ID
     * @return 삭제 결과  응답
     */
    public TechStackDeleteResponse deleteTechStackByProfileIdAndId(Long profileId, Long id) {
        // FK 검증
        validataProfileId(profileId);

        // ProfileId + ID에 따른 기술 스택이 있는지 확인
        techStackRepository.findByProfileIdAndId(profileId, id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        // 삭제 수행
        boolean isDeleted = techStackRepository.deleteByProfileIdAndId(profileId, id);

        // 삭제 결과 반환
        return TechStackDeleteResponse.of(id, isDeleted);
    }

    /**
     * 프로필별 기술 스택 전체 삭제
     *
     * @param profileId 삭제할 프로필 ID
     * @return 삭제 결과 응답
     */
    public Map<String, Object> deleteAllTechStackByProfileId(Long profileId) {
        // FK 검증
        validataProfileId(profileId);

        // 프로필 총 개수 확인
        long deleteCount = techStackRepository.deleteAllByProfileId(profileId);

        // 삭제 결과 반환
        return Map.of(
                "message", "전체 기술 스택이 성공적으로 삭제되었습니다.",
                "deletedCount", deleteCount
        );
    }

    // ==================== VALIDATION ====================

    /**
     * 프로필과 기술 스택의 FK 검증
     */
    private Profile validataProfileId(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));
    }

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

    /**
     * 수정 요청 유효성 검증
     */
    public void validataUpdateRequest(TechStackUpdateRequest request) {
        if (request.getName() != null) {
            if (request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("기술명은 빈 값일 수 없습니다.");
            }
            if (request.getName().length() > 50) {
                throw new IllegalArgumentException("기술명은 50자를 초과할 수 없습니다.");
            }
        }

        if (request.getCategory() != null && request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("기술 카테고리는 빈 값일 수 없습니다.");
        }

        if (request.getProficiency() != null && request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 빈 값일 수 없습니다.");
        }

        if (request.getYearsOfExp() != null && request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("사용 경험은 0년 이상이어야 합니다.");
        }
    }
}
