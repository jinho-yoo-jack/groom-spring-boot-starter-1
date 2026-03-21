package com.study.profile_stack_api.domain.tech_stack.dao;

import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;

import java.util.List;
import java.util.Optional;

public interface TechStackDao {
    TechStack save(TechStack techStack);

    Optional<TechStack> findTechStackById(Long profileId ,Long id);

    TechStack update(TechStack techStack);

    boolean existsById(Long id);

    boolean deleteById(Long id);

    Page<TechStack> findAllWithPaging(Long profileId, int page, int size);
}
