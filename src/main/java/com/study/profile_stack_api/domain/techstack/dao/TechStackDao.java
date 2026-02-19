package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;

import java.util.Optional;

public interface TechStackDao {
    //등록
    TechStack savetechstack(TechStack techStack);

    // 전체 개수 확인
    long countByProfileId(Long profileId);

    // 조회
    Page<TechStack> getAllTechStacksByProfileId(Long profileId, int page, int size);
    Optional<TechStack> getTechStackByIdAndProfileId(Long id, Long profileId);

    // 수정
    TechStack updateTechStack(Long id, Long profileId,  TechStack techStack);

    // 삭제
    boolean deleteTechStack( Long id, Long profileId);

    boolean existById(Long id);

}
