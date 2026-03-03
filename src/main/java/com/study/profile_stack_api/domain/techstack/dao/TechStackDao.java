package com.study.profile_stack_api.domain.techstack.dao;

import java.util.List;
import java.util.Optional;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;

public interface TechStackDao {
    TechStack save(TechStack techStack);

    Optional<TechStack> findById(Long id);

    List<TechStack> findByProfileId(Long profileId);

    List<TechStack> findByProfileId(Long profileId, int offset, int size);

    long countByProfileId(Long profileId);

    List<TechStack> findByProfileIdAndCategory(Long profileId, String category, int offset, int size);

    long countByProfileIdAndCategory(Long profileId, String category);

    List<TechStack> findByProfileIdAndProficiency(Long profileId, String proficiency, int offset, int size);

    long countByProfileIdAndProficiency(Long profileId, String proficiency);

    TechStack update(TechStack techStack);

    void deleteById(Long id);
}