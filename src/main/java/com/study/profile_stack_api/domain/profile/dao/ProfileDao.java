package com.study.profile_stack_api.domain.profile.dao;

import java.util.List;
import java.util.Optional;

import com.study.profile_stack_api.domain.profile.entity.Profile;

public interface ProfileDao {
    Profile save(Profile profile);

    Optional<Profile> findById(Long id);

    List<Profile> findAll(int offset, int size);

    Profile update(Profile profile);

    void deleteById(Long id);

    long count();

    boolean existsByEmail(String email);
}