package com.study.domain.profile.service;

import com.study.domain.profile.dao.ProfileDao;
import com.study.domain.profile.dto.request.ProfileCreateRequest;
import com.study.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.domain.profile.dto.response.ProfileResponse;
import com.study.domain.profile.entity.Position;
import com.study.domain.profile.entity.Profile;
import com.study.global.exception.DuplicateEmailException;
import com.study.global.exception.ProfileNotFoundException;
import com.study.global.common.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ProfileService {

    private final ProfileDao profileDao;

    // 페이징 관련 상수
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    public ProfileService(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    // ==================== CREATE ====================

    public ProfileResponse createProfile(ProfileCreateRequest request) {
        validateCreateRequest(request);

        String email = request.getEmail().trim();
        if (profileDao.countByEmail(email) > 0) {
            throw new DuplicateEmailException(email);
        }

        String normalizedPosition = Position.from(request.getPosition()).name();

        LocalDateTime now = LocalDateTime.now();

        Profile profile = new Profile(
                null,
                request.getName().trim(),
                email,
                request.getBio(),
                normalizedPosition,
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl(),
                now,
                now
        );

        Profile saved = profileDao.save(profile);
        return ProfileResponse.from(saved);
    }

    // ==================== READ ====================

    public ProfileResponse getProfileById(Long id) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        return ProfileResponse.from(profile);
    }

    /**
     * 목록 조회(페이징 + 필터)
     * GET /api/v1/profiles?page=0&size=10&position=BACKEND&name=김
     *
     * 명세 응답 형태는 공통 Page<T>가 그대로 만족:
     * { content, page, size, totalElements, totalPages, first, last, hasNext, hasPrevious }
     */
    public Page<ProfileResponse> getProfilesWithPaging(
            Integer page,
            Integer size,
            String position,
            String name
    ) {
        int p = normalizePage(page);
        int s = normalizeSize(size);

        // position 필터가 있으면 검증/정규화 (잘못된 값이면 IllegalArgumentException -> INVALID_INPUT)
        String normalizedPosition = null;
        if (position != null && !position.isBlank()) {
            normalizedPosition = Position.from(position).name();
        }

        // name 부분일치 검색
        String nameKeyword = (name != null && !name.isBlank()) ? name.trim() : null;

        // DAO 검색 + 페이징 (careerYears 범위는 명세에 없으니 null로)
        Page<Profile> profilePage = profileDao.searchWithPaging(
                nameKeyword,
                normalizedPosition,
                null,
                null,
                p,
                s
        );

        List<ProfileResponse> content = profilePage.getContent().stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());

        return new Page<>(content, p, s, profilePage.getTotalElements());
    }

    /**
     * 직무별 조회 (명세: 배열 반환)
     * GET /api/v1/profiles/position/{position}
     */
    public List<ProfileResponse> getProfilesByPosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("직무는 필수입니다.");
        }

        String normalizedPosition = Position.from(position).name();

        return profileDao.findByPosition(normalizedPosition).stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        validateUpdateRequest(request);

        // email 변경 시 중복 체크 (본인 이메일은 허용)
        String newEmail = null;
        if (request.getEmail() != null) {
            newEmail = request.getEmail().trim();
            if (!newEmail.equalsIgnoreCase(profile.getEmail())
                    && profileDao.countByEmail(newEmail) > 0) {
                throw new DuplicateEmailException(newEmail);
            }
        }

        // position 변경 시 검증/정규화
        String normalizedPosition = null;
        if (request.getPosition() != null) {
            normalizedPosition = Position.from(request.getPosition()).name();
        }

        profile.update(
                trimOrNull(request.getName()),
                newEmail, // null이면 업데이트 안 됨
                request.getBio(),
                normalizedPosition,
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl()
        );

        Profile updated = profileDao.update(profile);
        return ProfileResponse.from(updated);
    }

    // ==================== DELETE ====================

    public ProfileDeleteResponse deleteProfile(Long id) {
        if (!profileDao.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }
        profileDao.deleteById(id);
        return ProfileDeleteResponse.of(id);
    }

    // ==================== VALIDATION ====================

    private void validateCreateRequest(ProfileCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (request.getName().trim().length() > 50) {
            throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (request.getEmail().trim().length() > 100) {
            throw new IllegalArgumentException("이메일은 100자를 초과할 수 없습니다.");
        }
        if (!request.getEmail().contains("@")) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }

        if (request.getBio() != null && request.getBio().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }

        if (request.getPosition() == null || request.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("직무는 필수입니다.");
        }

        if (request.getCareerYears() == null) {
            throw new IllegalArgumentException("경력 연차는 필수입니다.");
        }
        if (request.getCareerYears() < 0) {
            throw new IllegalArgumentException("경력 연차는 0 이상이어야 합니다.");
        }
    }

    private void validateUpdateRequest(ProfileUpdateRequest request) {
        if (request.getName() != null) {
            if (request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
            }
            if (request.getName().trim().length() > 50) {
                throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
            }
        }

        if (request.getEmail() != null) {
            if (request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
            }
            if (request.getEmail().trim().length() > 100) {
                throw new IllegalArgumentException("이메일은 100자를 초과할 수 없습니다.");
            }
            if (!request.getEmail().contains("@")) {
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
            }
        }

        if (request.getBio() != null && request.getBio().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }

        if (request.getCareerYears() != null && request.getCareerYears() < 0) {
            throw new IllegalArgumentException("경력 연차는 0 이상이어야 합니다.");
        }
    }

    // ==================== UTIL ====================

    private int normalizePage(Integer page) {
        if (page == null) return 0;
        return Math.max(0, page);
    }

    private int normalizeSize(Integer size) {
        if (size == null) return DEFAULT_PAGE_SIZE;
        int s = Math.max(1, size);
        return Math.min(s, MAX_PAGE_SIZE);
    }

    private String trimOrNull(String v) {
        if (v == null) return null;
        String t = v.trim();
        return t.isEmpty() ? null : t;
    }
}
