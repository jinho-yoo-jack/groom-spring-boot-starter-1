package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.global.common.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProfileServiceIntegrationTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileDao profileDao;

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
    }

    @Test
    @DisplayName("프로필 생성 통합 테스트")
    void createProfile_Integration() {
        // given
        ProfileCreateRequest request = new ProfileCreateRequest();
        ReflectionTestUtils.setField(request, "name", "이몽룡");
        ReflectionTestUtils.setField(request, "email", "mong@test.com");
        ReflectionTestUtils.setField(request, "position", "BACKEND");
        ReflectionTestUtils.setField(request, "careerYears", 5);
        ReflectionTestUtils.setField(request, "bio", "안녕하세요");

        // when
        ProfileResponse response = profileService.createProfile(request);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("이몽룡");
        assertThat(response.getEmail()).isEqualTo("mong@test.com");
        assertThat(response.getPositionName()).isEqualTo("BACKEND");
        assertThat(response.getCareerYears()).isEqualTo(5);
        assertThat(response.getBio()).isEqualTo("안녕하세요");

        // DB 검증
        Profile profile = profileRepository.findById(response.getId()).orElseThrow();
        assertThat(profile.getName()).isEqualTo("이몽룡");
        assertThat(profile.getPosition()).isEqualTo(Position.BACKEND);
    }

    @Test
    @DisplayName("프로필 조회 및 수정 통합 테스트")
    void getAndUpdateProfile_Integration() {
        // given: 프로필 생성
        Profile profile = Profile.builder()
                .name("김연아")
                .email("yuna@test.com")
                .position(Position.FRONTEND)
                .careerYears(3)
                .build();
        Profile savedProfile = profileRepository.save(profile);

        // when: 프로필 조회 검증
        ProfileResponse getResponse = profileService.getProfileById(savedProfile.getId());
        assertThat(getResponse.getName()).isEqualTo("김연아");

        // given: 수정 요청 생성
        ProfileUpdateRequest updateRequest = ProfileUpdateRequest.builder()
                .name("김연아(수정됨)")
                .careerYears(4)
                .build();

        // when: 프로필 수정
        ProfileResponse updateResponse = profileService.updateProfile(savedProfile.getId(), updateRequest);

        // then: 수정 검증
        assertThat(updateResponse.getName()).isEqualTo("김연아(수정됨)");
        assertThat(updateResponse.getCareerYears()).isEqualTo(4);
        assertThat(updateResponse.getEmail()).isEqualTo("yuna@test.com"); // 변경되지 않은 값 유지

        // DB 검증
        Profile updatedProfile = profileRepository.findById(savedProfile.getId()).orElseThrow();
        assertThat(updatedProfile.getName()).isEqualTo("김연아(수정됨)");
        assertThat(updatedProfile.getCareerYears()).isEqualTo(4);
    }

    @Test
    @DisplayName("직무별 조회 통합 테스트")
    void getProfilesByPosition_Integration() {
        // given
        profileRepository.save(Profile.builder().name("A").email("a@test.com").position(Position.BACKEND).careerYears(1).build());
        profileRepository.save(Profile.builder().name("B").email("b@test.com").position(Position.BACKEND).careerYears(2).build());
        profileRepository.save(Profile.builder().name("C").email("c@test.com").position(Position.FRONTEND).careerYears(3).build());

        // when
        List<ProfileResponse> backendProfiles = profileService.getProfilesByPosition("BACKEND");
        List<ProfileResponse> frontendProfiles = profileService.getProfilesByPosition("FRONTEND");

        // then
        assertThat(backendProfiles).hasSize(2);
        assertThat(backendProfiles).extracting("name").containsExactlyInAnyOrder("A", "B");
        
        assertThat(frontendProfiles).hasSize(1);
        assertThat(frontendProfiles.get(0).getName()).isEqualTo("C");
    }

    @Test
    @DisplayName("프로필 삭제 통합 테스트")
    void deleteProfile_Integration() {
        // given
        Profile profile = profileRepository.save(Profile.builder()
                .name("삭제될프로필")
                .email("delete@test.com")
                .position(Position.DATA)
                .careerYears(1)
                .build());

        // when
        ProfileDeleteResponse response = profileService.deleteProfile(profile.getId());

        // then
        assertThat(response.getId()).isEqualTo(profile.getId());
        assertThat(profileRepository.findById(profile.getId())).isEmpty();
    }
}
