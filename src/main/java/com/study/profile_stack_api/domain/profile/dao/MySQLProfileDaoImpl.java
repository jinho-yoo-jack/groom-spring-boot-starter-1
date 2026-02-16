package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * MySQL 기반 Profile DAO 구현
 */
@Repository
public class MySQLProfileDaoImpl implements ProfileDao {
    public final JdbcTemplate jdbcTemplate;

    public MySQLProfileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==================== CREATE ====================

    @Override
    public Profile save(Profile profile) {
        String sql = """
                INSERT INTO profile
                (name, email, bio, position, career_years, github_url, blog_url)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getPosition().name());
            ps.setInt(5, profile.getCareerYears());
            ps.setString(6, profile.getGithubUrl());
            ps.setString(7, profile.getBlogUrl());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            profile.setId(generatedId.longValue());
        }

        return profile;
    }

    // ==================== READ ====================

    @Override
    public List<Profile> findAll() {
        String sql = "SELECT * FROM profile ORDER BY created_at DESC, id DESC";
        return jdbcTemplate.query(sql, profileRowMapper);
    }

    @Override
    public Optional<Profile> findById(Long id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        try {
            Profile profile = jdbcTemplate.queryForObject(sql, profileRowMapper, id);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findByPosition(Position position) {
        String sql = "SELECT * FROM profile WHERE position = ? ORDER BY created_at DESC, id DESC";
        return jdbcTemplate.query(sql, profileRowMapper, position.name());
    }

    // ==================== UPDATE ====================

    @Override
    public Profile update(Profile profile) {
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
            throw new ProfileNotFoundException(profile.getId());
        }

        return profile;
    }

    // ==================== DELETE ====================

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM profile WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM profile";
        return jdbcTemplate.update(sql);
    }

    // ==================== VALIDATION ====================

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM profile WHERE LOWER(email) = LOWER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM profile WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // ==================== PRIVATE METHODS ====================

    /**
     * ResultSet의 각 행을 Profile 객체로 변환
     */
    private final RowMapper<Profile> profileRowMapper = (rs, rowMapper) -> {
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
