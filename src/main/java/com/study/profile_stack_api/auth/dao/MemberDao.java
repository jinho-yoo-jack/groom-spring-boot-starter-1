package com.study.profile_stack_api.auth.dao;

import com.study.profile_stack_api.auth.entity.Member;

import java.sql.Timestamp;
import java.util.Optional;

public interface MemberDao {

    // ========== CREATE ==========
    Member save(Member user);

    // ========== READ ==========
    Optional<Member> findByUsername(String userName);

    Optional<Member> findByRefreshToken(String refreshToken);
}
