package com.study.profile_stack_api.domain.profile.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;

@Repository
public class ProfileDaoImpl implements ProfileDao {

    private final JdbcTemplate jdbcTemplate;

    public ProfileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Profile> profileRowMapper = (rs, rowNum) -> Profile.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .email(rs.getString("email"))
            .bio(rs.getString("bio"))
            .position(Position.valueOf(rs.getString("position")))
            .careerYears(rs.getInt("career_years"))
            .githubUrl(rs.getString("github_url"))
            .blogUrl(rs.getString("blog_url"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
            .build();

    @Override
    public Profile save(Profile profile) {
        String sql = "INSERT INTO profile (name, email, bio, position, career_years, github_url, blog_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

        Long generatedId = ((Number) keyHolder.getKeys().get("ID")).longValue();
        return findById(generatedId).orElseThrow();
    }

    @Override
    public Optional<Profile> findById(Long id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        List<Profile> results = jdbcTemplate.query(sql, profileRowMapper, id);
        return results.stream().findFirst();
    }

    @Override
    public List<Profile> findAll(int offset, int size) {
        String sql = "SELECT * FROM profile ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, profileRowMapper, size, offset);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM profile";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM profile WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }

    @Override
    public Profile update(Profile profile) {
        String sql = "UPDATE profile SET name = ?, email = ?, bio = ?, position = ?, career_years = ?, github_url = ?, blog_url = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql,
                profile.getName(),
                profile.getEmail(),
                profile.getBio(),
                profile.getPosition().name(),
                profile.getCareerYears(),
                profile.getGithubUrl(),
                profile.getBlogUrl(),
                profile.getId() // WHERE id = ? ← 마지막!
        );
        return findById(profile.getId()).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM profile WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}