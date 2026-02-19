package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;
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

    private static final int MAX_PAGE_SIZE = 100;

    // ================ CREATE ====================

    /**
     * 프로필 생성
     */
    public ProfileResponse save(ProfileRequest request) {

        return ProfileResponse.from(profileDao.save(new Profile(request)));
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
                .map(ProfileResponse::from)
                .collect(Collectors.toList());

        return new Page<>(content, page, size, profilePage.getTotalElements());
    }

    /**
     * 프로필 단건 조회
     * @param id
     * @return
     */
    public ProfileResponse getProfile(long id) {

        Profile profile = profileDao.getProfile(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + id + ")"));

        return ProfileResponse.from(profile);
    }

    /**
     * 직무별 프로필 조회
     * @param position
     * @return
     */
    public List<ProfileResponse> searchProfileByPosition(String position) {

        return profileDao.searchProfileByPosition(position).stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    // ================ UPDATE ====================
    /**
     * 프로필 수정
     * @param id
     * @param profileRequest
     * @return
     */
    public ProfileResponse updateProfile(long id, ProfileRequest profileRequest) {

        Profile profile = profileDao.getProfile(id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 프로필이 없습니다. (id : " + id + ")"));

        if (profileRequest.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        return ProfileResponse.from(profileDao.updateProfile(profile.update(profileRequest)));
    }

    // ================ DELETE ====================
    public ProfileDeleteResponse deleteProfileById(long id) {

        Profile profile = profileDao.getProfile(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 프로필이 없습니다. (id : " + id + ")"));

        profileDao.deleteProfileById(id);

        return ProfileDeleteResponse.of(id);
    }
}
