package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;

import java.util.List;
import java.util.Optional;

public interface ProfileDao {

    // ============ CREATE ================
    Profile save(Profile profile);

    // ============ READ ================
    Page<Profile> getAllProfiles(int page, int size, String position, String name);

    Optional<Profile> getProfile(long id);

    List<Profile> searchProfileByPosition(String position);

    Optional<Profile> getEmail(String email);
    // ============ UPDATE ================
    Profile updateProfile(Profile profile);

    // ============ DELETE ================
    void deleteProfileById(long id);
}
