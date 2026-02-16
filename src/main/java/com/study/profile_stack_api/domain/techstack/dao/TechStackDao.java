package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;

import java.util.List;
import java.util.Optional;

/**
 * TechStack DAO 인터페이스
 */
public interface TechStackDao {

    // ==================== CREATE ====================

    TechStack saveByProfileId(Long profileId, TechStack techStack);

    // ==================== READ ====================

    List<TechStack> findAllByProfileId(Long profileId);

    Optional<TechStack> findByProfileIdAndId(Long profileId, Long id);

    // ==================== UPDATE ====================

    TechStack updateByProfileId(Long profileId, TechStack techStack);

    // ==================== DELETE ====================

    boolean deleteByProfileIdAndId(Long profileId, Long id);

    int deleteAllByProfileId(Long profileId);

    // ==================== VALIDATION ====================

    boolean existsById(Long id);
}
