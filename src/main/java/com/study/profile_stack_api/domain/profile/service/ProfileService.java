package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.PageResponse;
import com.study.profile_stack_api.global.exception.DataNotFoundException;
import com.study.profile_stack_api.global.exception.DuplicateEmailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileDao profileDao;

    public ProfileResponse createProfile(ProfileRequest profileRequest) {

        Position position = Position.of(profileRequest.getPosition());
        String email = profileRequest.getEmail();

        if(profileDao.existsByEmail(email))
            throw new DuplicateEmailException(email);

        Profile profile = Profile.builder()
                .name(profileRequest.getName())
                .email(email)
                .bio(profileRequest.getBio())
                .position(position.name())
                .careerYears(profileRequest.getCareerYears())
                .githubUrl(profileRequest.getGithubUrl())
                .blogUrl(profileRequest.getBlogUrl())
                .build();

        Profile savedProfile = profileDao.saveProduct(profile);

        return ProfileResponse.from(savedProfile);
    }

    public PageResponse<ProfileResponse> findAllWithPaging(int page, int size) {
        Page<Profile> profilePage = profileDao.findAllWithPaging(page, size);

        List<ProfileResponse> profileResponseList = profilePage.getContent().stream()
                .map(ProfileResponse::from)
                .toList();

        return new PageResponse<>(
                profileResponseList,
                profilePage.getNumber(),
                profilePage.getSize(),
                profilePage.getTotalElements()
        );
    }

    public ProfileResponse getProfile(Long id) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new DataNotFoundException("프로필", id));

        return ProfileResponse.from(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long id,ProfileRequest profileRequest) {
        Position position = Position.of(profileRequest.getPosition());
        String email = profileRequest.getEmail();

        if(profileDao.existsByEmail(email))
            throw new DuplicateEmailException(email);

        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new DataNotFoundException("프로필", id));

        profile.setName(profileRequest.getName());
        profile.setEmail(profileRequest.getEmail());
        profile.setBio(profileRequest.getBio());
        profile.setPosition(position.getDescription());
        profile.setCareerYears(profileRequest.getCareerYears());
        profile.setGithubUrl(profileRequest.getGithubUrl());
        profile.setBlogUrl(profileRequest.getBlogUrl());

        return ProfileResponse.from(profile);
    }

    public List<ProfileResponse> findByPosition(String position) {
        List<Profile> profiles = profileDao.findByPosition(position);

        return (profiles != null) ?
                profiles.stream().map(ProfileResponse::from).toList()
                : List.of();
    }

    public void deleteProfile(Long id) {
        if(!profileDao.existsById(id))
            throw new DataNotFoundException("프로필", id);

        profileDao.deleteProfile(id);
    }
}
