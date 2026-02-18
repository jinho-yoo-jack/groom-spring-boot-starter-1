package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private ProfileDao profileDao;

    public ProfileService(ProfileDao profileDao) {this.profileDao = profileDao;}

    // Create
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        // TODO : validation 추가
        validationCreateRequest(request);

        Profile profile = new Profile(
                null,
                request.getName(),
                request.getEmail(),
                request.getBio(),
                Position.valueOf(request.getPosition()),
                request.getCareerYears(),
                request.getGithubUrl(),
                request.getBlogUrl());

        Profile savedProfile = profileDao.save(profile);
        return ProfileResponse.from(savedProfile);
    }

    // Read

    // getAll
    public List<ProfileResponse> getAllProfiles() {
        List<Profile> profiles = profileDao.findAll();

        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    // getById
    public ProfileResponse getProfilesById(Long id) {
        Profile profile = profileDao.fineById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID : " + id));

        return ProfileResponse.from(profile);
    }

    // getByPosition
    public List<ProfileResponse> getProfilesByPosition(String position) {
        List<Profile> profiles = profileDao.findByPosition(position);

        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
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



}
