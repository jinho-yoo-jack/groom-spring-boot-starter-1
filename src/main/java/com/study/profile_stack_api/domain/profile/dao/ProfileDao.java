package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;

import java.util.List;

public interface ProfileDao {
    // 프로필 생성
    Profile saveProduct(Profile profile);

    // 프로필 목록 조회

    // 프로필 단건 조회
    Profile findById(Long id);

    // 직무별 프로필 조회
    List<Profile> findByPosition(String position);

    // 프로필 삭제
    void deleteProfile(Long id);
}