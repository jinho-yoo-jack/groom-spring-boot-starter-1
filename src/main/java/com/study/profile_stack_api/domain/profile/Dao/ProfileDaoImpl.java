package com.study.profile_stack_api.domain.profile.Dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class ProfileDaoImpl implements ProfileDao {

    private final JdbcTemplate jdbcTemplate;

    // ============== CREATE =================
    @Override
    public Profile save(Profile profile) {
        String sql = """
                INSERT INTO profile (name, email, bio, position, career_years, github_url, blog_url, created_at) 
                VALUES (?, ?, ?, ?, ?, ? ,?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getPosition().name());
            ps.setInt(5, profile.getCareerYears());
            ps.setString(6, profile.getGithubUrl());
            ps.setString(7, profile.getBlogUrl());
            ps.setTimestamp(8, Timestamp.valueOf(profile.getCreatedAt()));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            profile.setId(generatedId.longValue());
        }

        return profile;
    }

    // ============== READ =================

    @Override
    public Page<Profile> getAllProfiles(int page, int size) {

        String countSql = "SELECT count(*) FROM profile";
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class);

        // 전체 데이터가 0건이면 빈 페이지 반환
        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String datasql = """
                SELECT * FROM profile 
                ORDER BY id DESC
                LIMIT ? OFFSET ?
                """;

        int offset = page * size;
        List<Profile> content = jdbcTemplate.query(datasql, profileRowMapper, size, offset);

        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<Profile> getProfile(long id) {

        String sql = "SELECT * FROM profile WHERE id = ?";

        try {
            Profile profile = jdbcTemplate.queryForObject(sql, profileRowMapper, id);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> searchProfileByPosition(String position) {

        String sql = "SELECT * FROM profile WHERE position = ?";

        List<Profile> content = jdbcTemplate.query(sql, profileRowMapper, position);
        return content;
    }

    // ============== UPDATE =================
    @Override
    public Profile updateProfile(Profile profile) {
        String sql = """
                UPDATE profile
                SET name = ?, email = ?, bio = ?, position = ?, career_years = ?, github_url = ?, blog_url = ?
                WHERE id = ?
                """;

        int updated = jdbcTemplate.update(sql,
                profile.getName(),
                profile.getEmail(),
                profile.getBio(),
                profile.getPosition().name(),
                profile.getCareerYears(),
                profile.getGithubUrl(),
                profile.getBlogUrl(),
                profile.getId());

        if (updated == 0) {
            throw new RuntimeException("profile not found. ID: " + profile.getId());
        }

        return profile;
    }

    // ===================================
    private final RowMapper<Profile> profileRowMapper = (rs, rowNum) -> {
        Profile profile = new Profile();
        profile.setId(rs.getLong("id"));
        profile.setName(rs.getString("name"));
        profile.setEmail(rs.getString("email"));
        profile.setBio(rs.getString("bio"));
        profile.setPosition(Position.valueOf(rs.getString("position")));
        profile.setCareerYears(rs.getInt("career_years"));
        profile.setGithubUrl(rs.getString("github_url"));
        profile.setBlogUrl(rs.getString("blog_url"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return profile;
    };
}
