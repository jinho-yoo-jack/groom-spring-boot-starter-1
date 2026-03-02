package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;
import com.study.profile_stack_api.global.exception.DuplicateEmailException;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import com.study.profile_stack_api.global.exception.UnauthorizedException;
import com.study.profile_stack_api.global.mapper.ProfileMapper;
import com.study.profile_stack_api.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileDao profileDao;
    private final ProfileMapper profileMapper;

    private static final int MAX_PAGE_SIZE = 100;

    // ================ CREATE ====================

    /**
     * 프로필 생성
     */
    public ProfileResponse save(ProfileRequest request) {

        Profile profile = profileMapper.toEntity(request);

        profileDao.getEmail(request.getEmail())
                .ifPresent(profileByEmail -> {
                    throw new DuplicateEmailException(profile.getEmail());
                });

        Long currentUserId = SecurityUtil.getCurrentUserId();
        profile.setMemberId(currentUserId);

        Profile savedProfile =  profileDao.save(profile);

        return profileMapper.toResponse(savedProfile);
    }

    // ================ READ ====================

    /**
     * 프로필 모두 조회
     * @param page
     * @param size
     * @return
     */
    public Page<ProfileResponse> getAllProfiles(int page, int size, String position, String name) {

        page = Math.max(0, page);
        size = Math.min(size, MAX_PAGE_SIZE);

        Page<Profile> profilePage = profileDao.getAllProfiles(page, size, position, name);

        List<ProfileResponse> content = profilePage.getContent().stream()
                .map(profileMapper::toResponse)
                .collect(Collectors.toList());

        return new Page<>(content, page, size, profilePage.getTotalElements());
    }

    /**
     * 프로필 단건 조회
     * @param id
     * @return
     */
    public ProfileResponse getProfile(long id) {

        return profileDao.getProfile(id)
                .map(profileMapper::toResponse)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }

    /**
     * 직무별 프로필 조회
     * @param position
     * @return
     */
    public List<ProfileResponse> searchProfileByPosition(String position) {

        return profileDao.searchProfileByPosition(position).stream()
                .map(profileMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ================ UPDATE ====================
    /**
     * 프로필 수정
     * @param id
     * @param profileUpdateRequest
     * @return
     */
    public ProfileResponse updateProfile(long id, ProfileUpdateRequest profileUpdateRequest) {

        Profile profile = profileDao.getProfile(id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 프로필이 없습니다. (id : " + id + ")"));

        if (profileUpdateRequest.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        Long currentUserId = SecurityUtil.getCurrentUserId();

        if (!currentUserId.equals(profile.getMemberId())) {
            throw new UnauthorizedException("본인 프로필만 수정 가능합니다.");
        }

        profileMapper.partialUpdate(profileUpdateRequest, profile);
        Profile savedProfile = profileDao.updateProfile(profile);

        return profileMapper.toResponse(savedProfile);
    }

    // ================ DELETE ====================
    public ProfileDeleteResponse deleteProfileById(long id) {

        Profile profile = profileDao.getProfile(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        Long currentUserId = SecurityUtil.getCurrentUserId();

        if (!currentUserId.equals(profile.getMemberId())) {
            throw new UnauthorizedException("본인 프로필만 삭제 가능합니다.");
        }

        profileDao.deleteProfileById(id);

        return ProfileDeleteResponse.of(id);
    }
}
