package com.study.profile_stack_api.domain.profile.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;

import com.study.profile_stack_api.global.common.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileDao profileDao;

    //페이징 관련 상수
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;


    public ProfileService(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    //create
    public ProfileResponse createProfile(ProfileCreateRequest request) {
        //유효성 검증
        validateCreateRequest(request);
        // email 중복 체크
        if(profileDao.existByEmail(request.email())){
            throw new IllegalStateException("DUPLIATE_EMAIL : 이미 사용중인 이메일입니다.");
        }

        // DTO -> entity 변환
        Profile profile = new Profile(
                null, request.name(),
                request.email(),
                request.bio(),
                request.position(),
                request.careerYears(),
                request.githubUrl(),
                request.blogUrl(),
                null,null
                );

        // 저장 (DAO 사용)
        Profile saveProfile = profileDao.save(profile);

        //Entity -> response DTO 변환 후 반환
        return ProfileResponse.from(saveProfile);
    }

    //read
    public List<ProfileResponse> getAllProfiles(){
        List<Profile> profiles = profileDao.findAll();
        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    public ProfileResponse getProfileById(Long id){
        return profileDao.findById(id)
                .map(ProfileResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필이 없습니다. id=" + id));
    }

    public List<ProfileResponse> getProfileByPosition(String position){
        List<Profile> profiles = profileDao.findByPosition(position);
        return profiles.stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());
    }

    //paging
    public Page<ProfileResponse> getProfileWithPaging(int page, int size){
        //파라미터 유효성 검증
        page = Math.max(0, page); //음수방지
        size = Math.min(Math.max(1,size), MAX_PAGE_SIZE); //1~100 범위

        //DAO 호출
        Page<Profile> profilePage = profileDao.findAllwithPaging(page, size);

        //entity -> dto 변환
        List<ProfileResponse> content = profilePage.getContent().stream()
                .map(ProfileResponse::from)
                .collect(Collectors.toList());

        return new Page<>(content, page, size, profilePage.getTotalElements());

    }


    //update
    public ProfileResponse updateProfile(Long id, ProfileUpdateRequest request) {
         //1. 기존 데이터 조회
        Optional<Profile> profileOpt = profileDao.findById(id);
        Profile profile = profileOpt.orElseThrow(()-> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다 (id : " + id + ")" )
        );

        //2. 수정할 내용이 있는지 확인
        if(request.hasNoUpdates()){
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        //3. 수정할 값 들의 유효성 검증
        validateUpdateRequest(request);


        //Entity 업데이트
        Profile updatedProfile = new Profile(
                id,
                request.name(),
                request.email(),
                request.bio(),
                request.position(),
                request.careerYears(),
                request.githubUrl(),
                request.blogUrl(),
                request.createdAt(),
                null //updatedAt은 DB에서 NOW()로 체움
        );

        profileDao.update(id, updatedProfile);

        return ProfileResponse.from(updatedProfile);
    }

    //delete
    public void deleteProfile(Long id){
        if(!profileDao.existsById(id)){
            throw new IllegalArgumentException("삭제 실패! ID " + id + "번 프로필이 존재하지 않습니다.");
        }

       profileDao.deleteById(id);
    }

    // 유효성 검증

    /**
     * 생성 요청 유효성 검증
     */
    private void validateCreateRequest(ProfileCreateRequest request) {
        if (request.name() == null || request.name().trim().isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (request.name().length() > 50) {
            throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
        }

        if (request.email() == null || request.email().trim().isBlank()) {
            throw new IllegalArgumentException("e-mail은 빈 값일 수 없습니다.");
        }
        if (request.email().length() > 100) {
            throw new IllegalArgumentException("e-mail은 100자를 초과할 수 없습니다.");
        }


        if (request.position() == null || request.position().trim().isBlank()) {
            throw new IllegalArgumentException("직무는 빈 값일 수 없습니다.");
        }
        if (request.position().length() > 20) {
            throw new IllegalArgumentException("직무는 20자를 초과할 수 없습니다.");

        }

        if (request.careerYears() != null) {
            if (request.careerYears() < 0) {
                throw new IllegalArgumentException("경력/연차는 0 이상이어야 합니다.");
            }
        } else {
            throw new IllegalArgumentException("경력/연차는 빈 값일 수 없습니다.");
        }

        if (request.bio() != null && request.bio().trim().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }
        if (request.githubUrl() != null && request.githubUrl().trim().length() > 200) {
            throw new IllegalArgumentException("git URL은 200자를 초과할 수 없습니다.");
        }
        if (request.blogUrl() != null && request.blogUrl().trim().length() > 200) {
            throw new IllegalArgumentException("Blog URL은 200자를 초과할 수 없습니다.");
        }
    }

    /**
     * 수정 요청 유효성 검증
     * null이 아닌 값만 검증합니다.
     */
    private void validateUpdateRequest(ProfileUpdateRequest request) {
        if (request.name() != null) {
            if (request.name().trim().isBlank()) {
                throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
            }
            if (request.name().length() > 50) {
                throw new IllegalArgumentException("이름은 50자를 초과할 수 없습니다.");
            }
        }

        if (request.email() != null) {
            if (request.email().trim().isBlank()) {
                throw new IllegalArgumentException("e-mail은 빈 값일 수 없습니다.");
            }
            if (request.email().length() > 100) {
                throw new IllegalArgumentException("e-mail은 100자를 초과할 수 없습니다.");
            }

            // 만약 email을 바꾸겠다고 하면 ?
            if (profileDao.existByEmail(request.email())){
                throw new IllegalArgumentException("이미 사용중인 이메일입니다.");

            }
        }

        if (request.position() != null) {
            if (request.position().trim().isBlank()) {
                throw new IllegalArgumentException("직무는 빈 값일 수 없습니다.");
            }
            if (request.position().length() > 20) {
                throw new IllegalArgumentException("직무는 20자를 초과할 수 없습니다.");
            }
        }

        if (request.careerYears() != null) {
            if (request.careerYears() < 0) {
                throw new IllegalArgumentException("경력/연차는 0 이상이어야 합니다.");
            }
        } else {
            throw new IllegalArgumentException("경력/연차는 빈 값일 수 없습니다.");
        }

        if (request.bio() != null && request.bio().trim().length() > 500) {
            throw new IllegalArgumentException("자기소개는 500자를 초과할 수 없습니다.");
        }
        if (request.githubUrl() != null && request.githubUrl().trim().length() > 200) {
            throw new IllegalArgumentException("git URL은 200자를 초과할 수 없습니다.");
        }
        if (request.blogUrl() != null && request.blogUrl().trim().length() > 200) {
            throw new IllegalArgumentException("Blog URL은 200자를 초과할 수 없습니다.");
        }
    }


}
