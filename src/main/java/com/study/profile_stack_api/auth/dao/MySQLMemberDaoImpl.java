package com.study.profile_stack_api.auth.dao;

import com.study.profile_stack_api.auth.entity.Member;
import com.study.profile_stack_api.auth.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MySQLMemberDaoImpl implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    // ======================= CREATE =======================

    @Override
    public Member save(Member member) {
        log.info("Saving Member : {}", member.getUsername());

        String sql = """
                INSERT INTO member (username, password, role)
                VALUES (?, ?, ?)
                """;

        // KeyHolder: Object to receive auto-generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getRole().name());
            return ps;
        }, keyHolder);

        // Set the generated ID to StudyLog object
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            member.setId(generatedId.longValue());
            log.info("Member saved with ID: {}", generatedId.longValue());
        }

        return member;
    }

    // ====================== Read ======================

    @Override
    public Optional<Member> findByUsername(String userName) {

        String sql = "SELECT * FROM member WHERE username = ?";

        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper, userName);
            return Optional.ofNullable(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Find user by refresh token
     */
    public Optional<Member> findByRefreshToken(String refreshToken) {
        String sql = """
                SELECT m.* FROM member m
                INNER JOIN refresh_token rt ON m.id = rt.member_id
                WHERE rt.token = ? AND rt.expiry_date > NOW()
                """;

        try {
            Member user = jdbcTemplate.queryForObject(sql, memberRowMapper, refreshToken);
            log.debug("Found user by refresh token");
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.debug("No user found with valid refresh token");
            return Optional.empty();
        }
    }

    // ====================== Mapper ======================
    private final RowMapper<Member> memberRowMapper = (rs, rowNum) ->
            Member.builder()
                    .id(rs.getLong("id"))
                    .password(rs.getString("password"))
                    .username(rs.getString("username"))
                    .role(MemberRole.valueOf(rs.getString("role")))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .build();
}
