package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.techstack.dao.TechStackDao;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechStackService {
    private final TechStackDao techStackDao;
    private final ProfileDao profileDao;

    public TechStackService(TechStackDao techStackDao, ProfileDao profileDao) {
        this.techStackDao = techStackDao;
        this.profileDao = profileDao;
    }

    public TechStackResponse create(Long profileId, TechStackCreateRequest request){
        // 프로필이 존재하는 지 확인
        if(!profileDao.existsById(profileId)){
            throw new ProfileNotFoundException(profileId);
        }


        //dto -> entitiy
        TechStack techStack = new TechStack(
            null, //id
                request.getProfileId(),
                request.getName(),
                request.getCategory(),
                request.getProficiency(),
                request.getYearsOfExp(),
                null, //createdAt
                null // updatedAt
                );
        techStack.setProfileId(profileId);
        //DAO를 통해 DB 저장
        TechStack saved = techStackDao.savetechstack(techStack);

        //저장된 entitiy를 dto로 반환
        return TechStackResponse.from(saved);
    }


    //특정 profileId의 모든 기술 스택 검색
    public Page<TechStackResponse> getList(Long profileId, int page, int size){
        //profile id 검증
        if(!profileDao.existsById(profileId)){
            throw new ProfileNotFoundException(profileId);
        }

        //page 반환
        Page<TechStack> techStackPage = techStackDao.getAllTechStacksByProfileId(profileId, page, size);

        //dto 반환
        List<TechStackResponse> contents = techStackPage.getContent().stream()
                .map(TechStackResponse::from)
                .toList();

        //페이지 반환
        return new Page<>(contents, page, size, techStackPage.getTotalElements());
    }


    // 특정 profileid 의 특정 id
    public TechStackResponse getById(Long profileId, Long id) {
        // 1. 프로필 존재 여부 확인 (기본 검증)
        if (!profileDao.existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }

        // 2. 단건 조회 실행
        TechStack techStack = techStackDao.getTechStackByIdAndProfileId(id, profileId)
                .orElseThrow(() -> new RuntimeException("해당 기술 스택을 찾을 수 없습니다. id: " + id));

        // 3. 응답 DTO로 변환하여 반환
        return TechStackResponse.from(techStack);
    }

    //특정 기술 스택 수정
    public TechStackResponse updateTechStack(Long profileId, Long id, TechStackUpdateRequest request){
        //변경 내용 확인
        if(request.hasNoUpdates()){
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        // 기본 검증
        validateRequest(request);

        //프로필 존재 확인
        if (!profileDao.existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }
        // id, profileId 미존재시 예외처리
        TechStack techStack = techStackDao.getTechStackByIdAndProfileId(id, profileId)
                .orElseThrow(() -> new TechStackNotFoundException(id));
        
        // 데이터 업데이트 (null 아닐 때만)
        if (request.getName() != null) techStack.setName(request.getName());
        if (request.getCategory() != null) techStack.setCategory(request.getCategory());
        if (request.getProficiency() != null) techStack.setProficiency(request.getProficiency());
        if (request.getYearsOfExp() != null) techStack.setYearsOfExp(request.getYearsOfExp());

        // profileId 수동 세팅
        techStack.setProfileId(profileId);

        // DAO 호출 -> 저장
        TechStack updated = techStackDao.updateTechStack(id, profileId, techStack);

        return TechStackResponse.from(updated);
    }


    //특정 기술 스택 삭제
    public void deleteTechStack(Long profileId, Long id) {
        //프로필 존재 확인
        if (!profileDao.existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }
        //데이터 존재 확인
        TechStack techStack = techStackDao.getTechStackByIdAndProfileId(id, profileId)
                .orElseThrow(() -> new TechStackNotFoundException(id));

        // 삭제 실행
        boolean deleted = techStackDao.deleteTechStack(id, profileId);

        // 만약 삭제된 데이터가 없다면 (id가 틀렸거나 해당 프로필의 기술이 아닌 경우)
        if (!deleted) {
            throw new RuntimeException("삭제할 기술 스택을 찾을 수 없습니다. id: " + id);
        }
    }


    // 유효성 검사
    private void validateRequest(TechStackCreateRequest request) {
        // name: 필수, 1~50자
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("이름은 50자 이내여야 합니다.");
        }

        // category: 필수, 정의된 값만 허용
        if (request.getCategory() == null) {
            throw new IllegalArgumentException("카테고리는 필수이며 정의된 값만 허용됩니다.");
        }

        // proficiency: 필수, 정의된 값만 허용
        if (request.getProficiency() == null) {
            throw new IllegalArgumentException("숙련도는 필수이며 정의된 값만 허용됩니다.");
        }

        // yearsOfExp: 필수, 0 이상의 정수
        if (request.getYearsOfExp() == null) {
            throw new IllegalArgumentException("경력/연차는 필수입니다.");
        }
        if (request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("경력/연차는 0 이상이어야 합니다.");
        }
    }



    // 유효성 검사
    private void validateRequest(TechStackUpdateRequest request) {
        // name: 필수, 1~50자
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (request.getName().length() > 50) {
            throw new IllegalArgumentException("이름은 50자 이내여야 합니다.");
        }

        // category: 필수, 정의된 값만 허용
        if (request.getCategory() == null) {
            throw new IllegalArgumentException("카테고리는 필수이며 정의된 값만 허용됩니다.");
        }

        // proficiency: 필수, 정의된 값만 허용
        if (request.getProficiency() == null) {
            throw new IllegalArgumentException("숙련도는 필수이며 정의된 값만 허용됩니다.");
        }

        // yearsOfExp: 필수, 0 이상의 정수
        if (request.getYearsOfExp() == null) {
            throw new IllegalArgumentException("경력/연차는 필수입니다.");
        }
        if (request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("경력/연차는 0 이상이어야 합니다.");
        }
    }
}
