package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;

import java.util.List;
import java.util.Optional;

public interface ProfileDao {
    //create
    Profile save(Profile profile);
    boolean existByEmail(String email);

    //read
    Optional<Profile> findById(Long id);
    List<Profile> findByPosition(String position);
    List<Profile> findAll();

    //update
    Profile update(Long id, Profile profile);

    //delete
    boolean deleteById(Long id);
    boolean existsById(Long id);


    //paging
    Page<Profile> findAllwithPaging(int page, int size);
    long count();

}
