package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;

import java.util.List;
import java.util.Optional;

/**
 * Profile DAO 인터페이스
 */
public interface ProfileDao {

    // ==================== CREATE ====================

    Profile save(Profile profile);

    // ==================== READ ====================

    List<Profile> findAll();

    Optional<Profile> findById(Long id);

    List<Profile> findByPosition(Position position);

    // ==================== UPDATE ====================

    Profile update(Profile profile);

    // ==================== DELETE ====================

    boolean deleteById(Long id);

    int deleteAll();

    // ==================== VALIDATION ====================

    boolean existsByEmail(String email);

    boolean existsById(Long id);
}
