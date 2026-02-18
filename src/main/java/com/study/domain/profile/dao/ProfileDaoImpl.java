package com.study.domain.profile.dao;

import com.study.domain.profile.entity.Profile;
import com.study.domain.profile.entity.Profile;
import com.study.global.common.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDaoImpl implements  ProfileDao {
    private final JdbcTemplate jdbcTemplate;

    public ProfileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========== CREATE ==========

    @Override
    public Profile save(Profile profile) {
        String sql = """
            INSERT INTO profile
            (name, email, bio, position, career_years, github_url, blog_url, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;


        KeyHolder keyHolder = new GeneratedKeyHolder();

        // createdAt / updatedAt이 null이면 DAO에서 채워줌
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = profile.getCreatedAt() != null ? profile.getCreatedAt() : now;
        LocalDateTime updatedAt = profile.getUpdatedAt() != null ? profile.getUpdatedAt() : now;

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getPosition()); // "BACKEND" 등
            ps.setInt(5, profile.getCareerYears() != null ? profile.getCareerYears() : 0);
            ps.setString(6, profile.getGithubUrl());
            ps.setString(7, profile.getBlogUrl());
            ps.setTimestamp(8, Timestamp.valueOf(createdAt));
            ps.setTimestamp(9, Timestamp.valueOf(updatedAt));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            profile.setId(generatedId.longValue());
        }
        profile.setCreatedAt(createdAt);
        profile.setUpdatedAt(updatedAt);

        return profile;
    }

    // ========== READ ==========

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
    public Optional<Profile> findByEmail(String email) {
        String sql = "SELECT * FROM profile WHERE email = ?";

        try {
            Profile profile = jdbcTemplate.queryForObject(sql, profileRowMapper, email);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findAll() {
        String sql = "SELECT * FROM profile ORDER BY created_at DESC, id DESC";
        return jdbcTemplate.query(sql, profileRowMapper);
    }

    @Override
    public List<Profile> findByPosition(String position) {
        String sql = "SELECT * FROM profile WHERE position = ? ORDER BY created_at DESC, id DESC";
        return jdbcTemplate.query(sql, profileRowMapper, normalizePosition(position));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM profile WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM profile";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public long countByPosition(String position) {
        String sql = "SELECT COUNT(*) FROM profile WHERE position = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, normalizePosition(position));
        return count != null ? count : 0;
    }

    @Override
    public long countByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM profile WHERE email = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, email);
        return count != null ? count : 0;
    }

    // ========== UPDATE ==========

    @Override
    public Profile update(Profile profile) {
        String sql = """
            UPDATE profile
            SET name = ?, email = ?, bio = ?, position = ?, career_years = ?,
                github_url = ?, blog_url = ?, updated_at = ?
            WHERE id = ?
            """;

        LocalDateTime updatedAt = LocalDateTime.now();

        int updated = jdbcTemplate.update(sql,
                profile.getName(),
                profile.getEmail(),
                profile.getBio(),
                normalizePosition(profile.getPosition()),
                profile.getCareerYears(),
                profile.getGithubUrl(),
                profile.getBlogUrl(),
                Timestamp.valueOf(updatedAt),
                profile.getId()
        );

        if (updated == 0) {
            throw new RuntimeException("Profile not found. ID: " + profile.getId());
        }

        profile.setUpdatedAt(updatedAt);
        return profile;
    }

    // ========== DELETE ==========

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM profile WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM profile";
        jdbcTemplate.update(sql);
    }

    // ========== PAGING ==========

    @Override
    public Page<Profile> findAllWithPaging(int page, int size) {
        String countSql = "SELECT COUNT(*) FROM profile";
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class);
        long total = totalElements != null ? totalElements : 0;

        String dataSql = """
            SELECT * FROM profile
            ORDER BY created_at DESC, id DESC
            LIMIT ? OFFSET ?
            """;

        int offset = page * size;
        List<Profile> content = jdbcTemplate.query(dataSql, profileRowMapper, size, offset);

        return new Page<>(content, page, size, total);
    }

    @Override
    public Page<Profile> findByPositionWithPaging(String position, int page, int size) {
        String normalized = normalizePosition(position);

        String countSql = "SELECT COUNT(*) FROM profile WHERE position = ?";
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, normalized);

        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String dataSql = """
            SELECT * FROM profile
            WHERE position = ?
            ORDER BY created_at DESC, id DESC
            LIMIT ? OFFSET ?
            """;

        int offset = page * size;
        List<Profile> content = jdbcTemplate.query(dataSql, profileRowMapper, normalized, size, offset);

        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Page<Profile> searchWithPaging(
            String nameKeyword,
            String position,
            Integer minCareerYears,
            Integer maxCareerYears,
            int page,
            int size
    ) {
        StringBuilder whereClause = new StringBuilder("WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nameKeyword != null && !nameKeyword.isBlank()) {
            whereClause.append(" AND name LIKE ?");
            params.add("%" + nameKeyword + "%");
        }

        if (position != null && !position.isBlank()) {
            whereClause.append(" AND position = ?");
            params.add(normalizePosition(position));
        }

        if (minCareerYears != null) {
            whereClause.append(" AND career_years >= ?");
            params.add(minCareerYears);
        }

        if (maxCareerYears != null) {
            whereClause.append(" AND career_years <= ?");
            params.add(maxCareerYears);
        }

        // COUNT
        String countSql = "SELECT COUNT(*) FROM profile " + whereClause;
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, params.toArray());

        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        // DATA + paging
        String dataSql = "SELECT * FROM profile "
                + whereClause
                + " ORDER BY created_at DESC, id DESC"
                + " LIMIT ? OFFSET ?";

        List<Object> dataParams = new ArrayList<>(params);
        dataParams.add(size);
        dataParams.add(page * size);

        List<Profile> content = jdbcTemplate.query(dataSql, profileRowMapper, dataParams.toArray());

        return new Page<>(content, page, size, totalElements);
    }

    // ========== PRIVATE METHODS ==========

    /**
     * Profile RowMapper
     */
    private final RowMapper<Profile> profileRowMapper = (rs, rowNum) -> {
        Profile profile = new Profile();

        profile.setId(rs.getLong("id"));
        profile.setName(rs.getString("name"));
        profile.setEmail(rs.getString("email"));
        profile.setBio(rs.getString("bio"));
        profile.setPosition(rs.getString("position"));
        profile.setCareerYears(rs.getInt("career_years"));
        profile.setGithubUrl(rs.getString("github_url"));
        profile.setBlogUrl(rs.getString("blog_url"));

        Timestamp createdTs = rs.getTimestamp("created_at");
        Timestamp updatedTs = rs.getTimestamp("updated_at");

        profile.setCreatedAt(createdTs != null ? createdTs.toLocalDateTime() : null);
        profile.setUpdatedAt(updatedTs != null ? updatedTs.toLocalDateTime() : null);

        return profile;
    };

    /**
     * position 입력 normalize
     * - "backend" -> "BACKEND"
     * - " BACKEND " -> "BACKEND"
     */
    private String normalizePosition(String position) {
        if (position == null) return null;
        return position.trim().toUpperCase();
    }
}
