package com.study.profile_stack_api.domain.profile.Dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;

import java.util.List;
import java.util.Optional;

public interface ProfileDao {

    // ============ CREATE ================
    Profile save(Profile profile);

    // ============ READ ================
    Page<Profile> getAllProfiles(int page, int size);

    Optional<Profile> getProfile(long id);

    List<Profile> searchProfileByPosition(String position);

    // ============ UPDATE ================
    Profile updateProfile(Profile profile);
}
