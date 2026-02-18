package com.study.domain.techStack.service;

import com.study.domain.profile.dao.ProfileDao;
import com.study.global.exception.ProfileNotFoundException;
import com.study.global.exception.TechStackNotFoundException;
import com.study.domain.techStack.dto.request.TechStackCreateRequest;
import com.study.domain.techStack.dto.request.TechStackUpdateRequest;
import com.study.domain.techStack.dto.response.TechStackResponse;
import com.study.domain.techStack.entity.Proficiency;
import com.study.domain.techStack.entity.TechCategory;
import com.study.domain.techStack.entity.TechStack;
import com.study.domain.techStack.repository.TechStackRepository;
import com.study.global.common.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TechStackService {

    private final TechStackRepository repository;
    private final ProfileDao profileDao;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    public TechStackService(TechStackRepository repository, ProfileDao profileDao) {
        this.repository = repository;
        this.profileDao = profileDao;
    }

    // ===== CREATE =====
    public TechStackResponse createTechStack(Long profileId, TechStackCreateRequest request) {
        validateProfileExists(profileId);
        validateCreateRequest(request);

        String category = TechCategory.from(request.getCategory()).name();
        String proficiency = Proficiency.from(request.getProficiency()).name();

        LocalDateTime now = LocalDateTime.now();

        TechStack techStack = new TechStack(
                null,
                profileId,
                request.getName().trim(),
                category,
                proficiency,
                request.getYearsOfExp(),
                now,
                now
        );

        return TechStackResponse.from(repository.save(techStack));
    }

    // ===== READ (Paging) =====
    public Page<TechStackResponse> getTechStacks(Long profileId, Integer page, Integer size) {
        validateProfileExists(profileId);

        int p = normalizePage(page);
        int s = normalizeSize(size);

        Page<TechStack> entityPage = repository.findAllByProfileIdWithPaging(profileId, p, s);

        List<TechStackResponse> content = entityPage.getContent().stream()
                .map(TechStackResponse::from)
                .collect(Collectors.toList());

        return new Page<>(content, p, s, entityPage.getTotalElements());
    }

    // ===== READ (Single) =====
    public TechStackResponse getTechStack(Long profileId, Long id) {
        validateProfileExists(profileId);

        TechStack techStack = repository.findById(profileId, id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        return TechStackResponse.from(techStack);
    }

    // ===== UPDATE =====
    public TechStackResponse updateTechStack(Long profileId, Long id, TechStackUpdateRequest request) {
        Objects.requireNonNull(profileId);
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        validateProfileExists(profileId);

        if (request.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }
        validateUpdateRequest(request);

        TechStack techStack = repository.findById(profileId, id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        String category = null;
        if (request.getCategory() != null) category = TechCategory.from(request.getCategory()).name();

        String proficiency = null;
        if (request.getProficiency() != null) proficiency = Proficiency.from(request.getProficiency()).name();

        techStack.update(
                trimOrNull(request.getName()),
                category,
                proficiency,
                request.getYearsOfExp()
        );

        return TechStackResponse.from(repository.update(techStack));
    }

    // ===== DELETE =====
    public TechStackResponse deleteTechStack(Long profileId, Long id) {
        validateProfileExists(profileId);

        if (!repository.existsById(profileId, id)) {
            throw new TechStackNotFoundException(id);
        }

        repository.deleteById(profileId, id);
        return TechStackResponse.ofDeleted(id);
    }

    // ===== VALIDATION =====

    private void validateProfileExists(Long profileId) {
        if (!profileDao.existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }
    }

    private void validateCreateRequest(TechStackCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("기술 스택 이름은 필수입니다.");
        }
        if (request.getName().trim().length() > 50) {
            throw new IllegalArgumentException("기술 스택 이름은 50자를 초과할 수 없습니다.");
        }
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }
        if (request.getProficiency() == null || request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 필수입니다.");
        }
        if (request.getYearsOfExp() == null) {
            throw new IllegalArgumentException("경력(년수)은 필수입니다.");
        }
        if (request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("경력(년수)은 0 이상이어야 합니다.");
        }
        // category/proficiency 값은 from()이 검증(잘못되면 IllegalArgumentException)
        TechCategory.from(request.getCategory());
        Proficiency.from(request.getProficiency());
    }

    private void validateUpdateRequest(TechStackUpdateRequest request) {
        if (request.getName() != null) {
            if (request.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("기술 스택 이름은 빈 값일 수 없습니다.");
            }
            if (request.getName().trim().length() > 50) {
                throw new IllegalArgumentException("기술 스택 이름은 50자를 초과할 수 없습니다.");
            }
        }
        if (request.getYearsOfExp() != null && request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("경력(년수)은 0 이상이어야 합니다.");
        }
        if (request.getCategory() != null) TechCategory.from(request.getCategory());
        if (request.getProficiency() != null) Proficiency.from(request.getProficiency());
    }

    // ===== UTIL =====

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
