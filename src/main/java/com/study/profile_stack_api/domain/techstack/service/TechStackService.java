package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.techstack.dao.TechStackDao;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;
import com.study.profile_stack_api.global.exception.UnauthorizedException;
import com.study.profile_stack_api.global.mapper.TechStackMapper;
import com.study.profile_stack_api.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackDao techstackDao;
    private final ProfileDao profileDao;
    private final TechStackMapper techStackMapper;

    private static final int MAX_PAGE_SIZE = 100;

    //=============== CREATE ====================
    /**
     * 기술 스택 저장
     * @param profileId
     * @param techstackRequest
     * @return
     */
    public TechStackResponse save(long profileId, TechStackRequest techstackRequest) {

        Profile profile = profileDao.getProfile(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + profileId + ")"));

        Long currentUserId = SecurityUtil.getCurrentUserId();

        if (!currentUserId.equals(profile.getMemberId())) {
            throw new UnauthorizedException("본인 기술스택만 입력 가능합니다.");
        }

        techstackRequest.setProfileId(profileId);
        TechStack techStack = techStackMapper.toEntity(techstackRequest);

        techstackDao.save(techStack);

        return techStackMapper.toResponse(techStack);
    }

    //=============== READ ====================

    /**
     * 기술 스택 목록 조회
     *
     * @param profileId
     * @param size
     * @param page
     * @param category
     * @param proficiency
     * @return
     */
    public Page<TechStackResponse> getAllTechStack(long profileId, int size, int page, String category, String proficiency) {

        profileDao.getProfile(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + profileId + ")"));

        page = Math.max(0, page);
        size = Math.min(size, MAX_PAGE_SIZE);

        Page<TechStack> techStackPage = techstackDao.getAllTechStacks(profileId, page, size, category, proficiency);

        List<TechStackResponse> content = techStackPage.getContent().stream()
                                            .map(techStackMapper::toResponse)
                                            .collect(Collectors.toList());

        return new Page<>(content, page, size, techStackPage.getTotalElements());
    }

    /**
     * 기술 스택 단건 조회
     * @param profileId
     * @param id
     * @return
     */
    public TechStackResponse getTechStack(long profileId, long id) {

        profileDao.getProfile(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));

        TechStack techStack = techstackDao.getTechStack(id)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        return techStackMapper.toResponse(techStack);
    }

    //=============== UPDATE ====================

    /**
     * 기술 스택 수정
     * @param profileId
     * @param id
     * @param techStackRequest
     * @return
     */
    public TechStackResponse updateTechStack(long profileId, long id, TechStackRequest techStackRequest) {
        Profile profile = profileDao.getProfile(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + profileId + ")"));

        TechStack techStack = techstackDao.getTechStack(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기술스택을 찾을 수 없습니다. (id : " + id + ")"));

        if (techStackRequest.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        Long currentUserId = SecurityUtil.getCurrentUserId();

        if (!currentUserId.equals(profile.getMemberId())) {
            throw new UnauthorizedException("본인 기술스택만 수정 가능합니다.");
        }

        techStackMapper.partialUpdate(techStackRequest, techStack);
        techstackDao.updateTechStack(profileId, id, techStack);

        return techStackMapper.toResponse(techStack);
    }


    public TechStackDeleteResponse deleteTechStack(long profileId, long id) {

        Profile profile = profileDao.getProfile(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + profileId + ")"));

        techstackDao.getTechStack(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기술스택을 찾을 수 없습니다. (id : " + id + ")"));

        Long currentUserId = SecurityUtil.getCurrentUserId();

        if (!currentUserId.equals(profile.getMemberId())) {
            throw new UnauthorizedException("본인 기술스택만 삭제 가능합니다.");
        }

        techstackDao.deleteTechStackById(id);

        return TechStackDeleteResponse.of(id);
    }
}
