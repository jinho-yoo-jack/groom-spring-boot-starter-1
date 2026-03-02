package com.study.profile_stack_api.auth.dao;

import com.study.profile_stack_api.auth.entity.Member;
import com.study.profile_stack_api.auth.entity.MemberRole;
import com.study.profile_stack_api.auth.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MYSQLRefreshTokenDao implements RefreshTokenDao {

    private final JdbcTemplate jdbcTemplate;

    /**
     * saveRefreshToken
     */
    public void saveRefreshToken(Long memberId, String token, Timestamp expiresAt) {
        // First, delete any existing refresh tokens for this user
        String deleteSql = "DELETE FROM refresh_token WHERE member_id = ?";
        jdbcTemplate.update(deleteSql, memberId);

        // Insert new refresh token
        String insertSql = "INSERT INTO refresh_token (member_id, token, expiry_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, memberId, token, expiresAt);
        log.debug("Saved refresh token for user ID: {}", memberId);
    }

    /**
     * Delete refresh token
     */
    public void deleteRefreshToken(String refreshToken) {
        String sql = "DELETE FROM refresh_token WHERE token = ?";
        jdbcTemplate.update(sql, refreshToken);
        log.debug("Deleted refresh token");
    }
}
