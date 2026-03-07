package com.study.profile_stack_api.domain.tech_stack.dao;

import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;

import java.util.List;
import java.util.Optional;

public interface TechStackDao {
    TechStack save(TechStack techStack);

    List<TechStack> findAll(Long profileId);

    Optional<TechStack> findTechStackById(Long profileId ,Long id);

    TechStack update(TechStack techStack);

    boolean exitsById(Long id);

    boolean deleteById(Long id);
}
