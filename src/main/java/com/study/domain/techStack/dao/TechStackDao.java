package com.study.domain.techStack.dao;

import com.study.domain.techStack.entity.TechStack;
import com.study.global.common.Page;

import java.util.Optional;

public interface TechStackDao {

    TechStack save(TechStack techStack);

    Page<TechStack> findAllByProfileIdWithPaging(Long profileId, int page, int size);

    Optional<TechStack> findById(Long profileId, Long id);

    TechStack update(TechStack techStack);

    boolean deleteById(Long profileId, Long id);

    boolean existsById(Long profileId, Long id);

    long countByProfileId(Long profileId);
}