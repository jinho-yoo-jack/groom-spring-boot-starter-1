package com.study.domain.techStack.dao;

import com.study.domain.techStack.entity.TechStack;
import com.study.global.common.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//mysql을 위함
@Repository
public class TechStackDaoImpl implements TechStackDao {

    private final JdbcTemplate jdbcTemplate;

    public TechStackDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TechStack save(TechStack techStack) {
        String sql = """
            INSERT INTO tech_stack
            (profile_id, name, category, proficiency, years_of_exp, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = techStack.getCreatedAt() != null ? techStack.getCreatedAt() : now;
        LocalDateTime updatedAt = techStack.getUpdatedAt() != null ? techStack.getUpdatedAt() : now;

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, techStack.getProfileId());
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getCategory());
            ps.setString(4, techStack.getProficiency());
            ps.setInt(5, techStack.getYearsOfExp());
            ps.setTimestamp(6, Timestamp.valueOf(createdAt));
            ps.setTimestamp(7, Timestamp.valueOf(updatedAt));
            return ps;
        }, keyHolder);

        Number id = keyHolder.getKey();
        if (id != null) techStack.setId(id.longValue());
        techStack.setCreatedAt(createdAt);
        techStack.setUpdatedAt(updatedAt);

        return techStack;
    }

    @Override
    public Page<TechStack> findAllByProfileIdWithPaging(Long profileId, int page, int size) {
        String countSql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ?";
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, profileId);
        long totalElements = total != null ? total : 0;

        if (totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String dataSql = """
            SELECT * FROM tech_stack
            WHERE profile_id = ?
            ORDER BY created_at DESC, id DESC
            LIMIT ? OFFSET ?
            """;

        int offset = page * size;
        List<TechStack> content = jdbcTemplate.query(dataSql, techStackRowMapper, profileId, size, offset);

        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<TechStack> findById(Long profileId, Long id) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? AND id = ?";
        try {
            TechStack t = jdbcTemplate.queryForObject(sql, techStackRowMapper, profileId, id);
            return Optional.ofNullable(t);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TechStack update(TechStack techStack) {
        String sql = """
            UPDATE tech_stack
            SET name = ?, category = ?, proficiency = ?, years_of_exp = ?, updated_at = ?
            WHERE profile_id = ? AND id = ?
            """;

        LocalDateTime updatedAt = LocalDateTime.now();

        int updated = jdbcTemplate.update(
                sql,
                techStack.getName(),
                techStack.getCategory(),
                techStack.getProficiency(),
                techStack.getYearsOfExp(),
                Timestamp.valueOf(updatedAt),
                techStack.getProfileId(),
                techStack.getId()
        );

        if (updated == 0) {
            throw new RuntimeException("TechStack not found. profileId=" + techStack.getProfileId() + ", id=" + techStack.getId());
        }

        techStack.setUpdatedAt(updatedAt);
        return techStack;
    }

    @Override
    public boolean deleteById(Long profileId, Long id) {
        String sql = "DELETE FROM tech_stack WHERE profile_id = ? AND id = ?";
        return jdbcTemplate.update(sql, profileId, id) > 0;
    }

    @Override
    public boolean existsById(Long profileId, Long id) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ? AND id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, profileId, id);
        return count != null && count > 0;
    }

    @Override
    public long countByProfileId(Long profileId) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, profileId);
        return count != null ? count : 0;
    }

    private final RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
        TechStack t = new TechStack();
        t.setId(rs.getLong("id"));
        t.setProfileId(rs.getLong("profile_id"));
        t.setName(rs.getString("name"));
        t.setCategory(rs.getString("category"));
        t.setProficiency(rs.getString("proficiency"));
        t.setYearsOfExp(rs.getInt("years_of_exp"));

        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        t.setCreatedAt(created != null ? created.toLocalDateTime() : null);
        t.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);

        return t;
    };
}
