package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProfileDao {
    // 프로필 생성
    Profile saveProduct(Profile profile);

    // 프로필 목록 조회
    Page<Profile> findAllWithPaging(int page, int size);

    // 프로필 단건 조회
    Optional<Profile> findById(Long id);

    // 직무별 프로필 조회
    List<Profile> findByPosition(String position);

    // 프로필 삭제
    void deleteProfile(Long id);

    // 이메일 중복 여부
    boolean existsByEmail(String email);

    // id 존재 여부
    boolean existsById(Long id);
}