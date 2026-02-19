package com.study.profile_stack_api.domain.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.global.common.Page;
import com.study.profile_stack_api.global.exception.DuplicateEmailException;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileResponse createProfile(ProfileCreateRequest request) {
        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        Profile profile = Profile.builder()
                .name(request.getName())
                .email(request.getEmail())
                .bio(request.getBio())
                .position(Position.valueOf(request.getPosition()))
                .careerYears(request.getCareerYears())
                .githubUrl(request.getGithubUrl())
                .blogUrl(request.getBlogUrl())
                .build();

        Profile saved = profileRepository.save(profile);
        return ProfileResponse.from(saved);
    }

    public ProfileResponse getProfile(Long id) {
        Profile profile = profileRepository.findById(id);
        return ProfileResponse.from(profile);
    }

    public Page<ProfileResponse> getProfiles(int page, int size) {
        List<Profile> profiles = profileRepository.findAll(page, size);
        long totalElements = profileRepository.count();
        List<ProfileResponse> content = profiles.stream().map(ProfileResponse::from).toList();

        return Page.of(content, page, size, totalElements);
    }

    public void deleteProfile(Long id) {
        profileRepository.findById(id);
        profileRepository.deleteById(id);
    }

    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        profileRepository.findById(id);

        Profile profile = Profile.builder()
                .id(id)
                .name(request.getName())
                .email(request.getEmail())
                .bio(request.getBio())
                .position(Position.valueOf(request.getPosition()))
                .careerYears(request.getCareerYears())
                .githubUrl(request.getGithubUrl())
                .blogUrl(request.getBlogUrl())
                .build();

        Profile updated = profileRepository.update(profile);
        return ProfileResponse.from(updated);
    }
}