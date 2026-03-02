package com.study.profile_stack_api.auth.dao;

import com.study.profile_stack_api.auth.entity.Member;

import java.sql.Timestamp;
import java.util.Optional;

public interface RefreshTokenDao {

    void saveRefreshToken(Long memberId, String token, Timestamp expiresAt);

    void deleteRefreshToken(String refreshToken);
}
