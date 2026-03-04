package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileDao profileDao;

    // Create
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        validationCreateRequest(request);

        Profile profile = Profile.builder()
                .id(null)
                .name(request.getName())
                .email(request.getEmail())
                .bio(request.getBio())
                .position(Position.valueOf(request.getPosition().toUpperCase()))
                .careerYears(request.getCareerYears())
                .githubUrl(request.getGithubUrl())
                .blogUrl(request.getBlogUrl())
                .build();

        Profile savedProfile = profileDao.save(profile);
        return ProfileResponse.from(savedProfile);
    }

    // Read
    public List<ProfileResponse> getAllProfiles() {
        List<Profile> profiles = profileDao.findAll();

        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    public ProfileResponse getProfilesById(Long id) {
        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID : " + id));

        return ProfileResponse.from(profile);
    }


    public List<ProfileResponse> getProfilesByPosition(String position) {
        List<Profile> profiles = profileDao.findByPosition(position);

        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    // Update
    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(request);

        Profile profile = profileDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. ID: " + id));

        if (request.hasNoUpdates()) {
            throw new IllegalArgumentException("수정할 내용이 존재하지 않습니다");
        }

        validationUpdateRequest(request);

        Position position = null;

        if(request.getPosition() != null) {
            try {
                position = Position.valueOf(request.getPosition().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("유효하지 않은 직무입니다");
            }
        }

        profile.update(
                request.getName(),
                request.getEmail(),
                request.getBio(),
                position,
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl()
        );

        Profile updatedProfile = profileDao.update(profile);
        return ProfileResponse.from(updatedProfile);
    }

    // Delete
    public ProfileDeleteResponse deleteProfile(Long id) {
        if (!profileDao.existById(id)) {
            throw new ResourceNotFoundException(id);
        }

        profileDao.deleteById(id);

        return ProfileDeleteResponse.of(id);
    }

    // validation
    public void validationCreateRequest(ProfileCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 공백일 수 없습니다.");
        }
        if (request.getPosition() == null || request.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("직무는 공백일 수 없습니다.");
        }
        if (request.getCareerYears() < 0 || request.getCareerYears() > 100) {
            throw new IllegalArgumentException("올바른 경력 연차를 입력해주세요.");
        }
    }

    private void validationUpdateRequest(ProfileUpdateRequest request) {
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다");
        }
        if (request.getEmail() != null && request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 공백일 수 없습니다.");
        }
        if (request.getPosition() != null && request.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("직무는 공백일 수 없습니다.");
        }
        if (request.getCareerYears() < 0 || request.getCareerYears() > 100) {
            throw new IllegalArgumentException("올바른 경력 연차를 입력해주세요.");
        }
    }

}
