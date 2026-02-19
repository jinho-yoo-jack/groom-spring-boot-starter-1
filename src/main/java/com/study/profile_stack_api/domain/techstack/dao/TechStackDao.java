package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.PageResponse;

import java.util.Optional;

public interface TechStackDao {
    // 기술 스택 추가
    TechStack saveTechStack(TechStack techStack);

    // 기술 스택 목록 조회
    PageResponse<TechStack> findAllWithPaging(int page, int size, Long profileId);

    // 기술 스택 단건 조회
    Optional<TechStack> findById(Long profileId, Long id);

    // 기술 스택 수정
    TechStack updateTechStack(Long profileId, Long id, TechStack techStack);

    // 기술 스택 삭제
    void deleteTechStack(Long profileId, Long id);

    public boolean existsById(Long id);
}
