package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.domain.profile.mapper.ProfileMapper;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.global.common.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileDao profileDao;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Test
    @DisplayName("프로필 생성 성공")
    void createProfile_Success() {
        // given
        ProfileCreateRequest request = new ProfileCreateRequest();
        ReflectionTestUtils.setField(request, "name", "홍길동");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "position", "BACKEND");
        ReflectionTestUtils.setField(request, "careerYears", 3);

        Profile profile = Profile.builder()
                .name("홍길동")
                .email("test@test.com")
                .position(Position.BACKEND)
                .careerYears(3)
                .build();

        Profile savedProfile = Profile.builder()
                .id(1L)
                .name("홍길동")
                .email("test@test.com")
                .position(Position.BACKEND)
                .careerYears(3)
                .build();

        ProfileResponse response = ProfileResponse.builder()
                .id(1L)
                .name("홍길동")
                .email("test@test.com")
                .positionName("BACKEND")
                .careerYears(3)
                .build();

        given(profileMapper.toEntity(request)).willReturn(profile);
        given(profileRepository.save(profile)).willReturn(savedProfile);
        given(profileMapper.toResponse(savedProfile)).willReturn(response);

        // when
        ProfileResponse result = profileService.createProfile(request);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("홍길동");
        assertThat(result.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("프로필 생성 실패 - 유효성 검사 에러")
    void createProfile_Fail_Validation() {
        // given
        ProfileCreateRequest request = new ProfileCreateRequest();
        ReflectionTestUtils.setField(request, "name", " ");
        
        // when & then
        assertThatThrownBy(() -> profileService.createProfile(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 공백일 수 없습니다.");
    }

    @Test
    @DisplayName("ID로 프로필 조회 성공")
    void getProfilesById_Success() {
        // given
        Long id = 1L;
        Profile profile = Profile.builder().id(id).name("홍길동").build();
        ProfileResponse response = ProfileResponse.builder().id(id).name("홍길동").build();

        given(profileRepository.findById(id)).willReturn(Optional.of(profile));
        given(profileMapper.toResponse(profile)).willReturn(response);

        // when
        ProfileResponse result = profileService.getProfileById(id);

        // then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("ID로 프로필 조회 실패 - 존재하지 않는 ID")
    void getProfilesById_Fail_NotFound() {
        // given
        Long id = 1L;
        given(profileRepository.findById(id)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> profileService.getProfileById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 ID");
    }

    @Test
    @DisplayName("직무로 프로필 목록 조회 성공")
    void getProfilesByPosition_Success() {
        // given
        String positionName = "BACKEND";
        Profile profile = Profile.builder().position(Position.BACKEND).build();
        ProfileResponse response = ProfileResponse.builder().positionName("BACKEND").build();

        given(profileRepository.findByPosition(Position.BACKEND)).willReturn(List.of(profile));
        given(profileMapper.toResponseList(List.of(profile))).willReturn(List.of(response));

        // when
        List<ProfileResponse> results = profileService.getProfilesByPosition(positionName);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getPositionName()).isEqualTo("BACKEND");
    }

    @Test
    @DisplayName("프로필 수정 성공")
    void updateProfile_Success() {
        // given
        Long id = 1L;
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .name("김철수")
                .build();
        
        Profile profile = Profile.builder().id(id).name("홍길동").build();
        ProfileResponse response = ProfileResponse.builder().id(id).name("김철수").build();

        given(profileRepository.findByIdOptimized(id)).willReturn(Optional.of(profile));
        given(profileMapper.toResponse(profile)).willReturn(response);

        // when
        ProfileResponse result = profileService.updateProfile(id, request);

        // then
        verify(profileMapper).updateEntityFromRequest(request, profile);
        assertThat(result.getName()).isEqualTo("김철수");
    }

    @Test
    @DisplayName("프로필 수정 실패 - 수정할 내용 없음")
    void updateProfile_Fail_NoUpdates() {
        // given
        Long id = 1L;
        ProfileUpdateRequest request = new ProfileUpdateRequest(); // hasNoUpdates() will return true
        Profile profile = Profile.builder().id(id).build();

        given(profileRepository.findByIdOptimized(id)).willReturn(Optional.of(profile));

        // when & then
        assertThatThrownBy(() -> profileService.updateProfile(id, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정할 내용이 존재하지 않습니다");
    }

    @Test
    @DisplayName("프로필 삭제 성공")
    void deleteProfile_Success() {
        // given
        Long id = 1L;
        given(profileRepository.existsById(id)).willReturn(true);

        // when
        ProfileDeleteResponse response = profileService.deleteProfile(id);

        // then
        verify(profileRepository).deleteById(id);
        assertThat(response.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("프로필 삭제 실패 - 존재하지 않는 프로필")
    void deleteProfile_Fail_NotFound() {
        // given
        Long id = 1L;
        given(profileRepository.existsById(id)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> profileService.deleteProfile(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }
    
    @Test
    @DisplayName("프로필 페이징 조회 성공")
    void getProfilesWithPaging_Success() {
        // given
        int page = 0;
        int size = 10;
        
        Profile profile = Profile.builder()
                .id(1L)
                .name("user")
                .email("test@email.com")
                .position(Position.BACKEND)
                .careerYears(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

                
        Page<Profile> profilePage = new Page<>(List.of(profile), page, size, 1);
        
        given(profileDao.findAllWithPaging(page, size)).willReturn(profilePage);
        
        // when
        Page<ProfileResponse> result = profileService.getProfilesWithPaging(page, size);
        
        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }
}
