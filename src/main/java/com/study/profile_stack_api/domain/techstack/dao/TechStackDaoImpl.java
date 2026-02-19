package com.study.profile_stack_api.domain.techstack.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.study.profile_stack_api.domain.techstack.entity.Category;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;

@Repository
public class TechStackDaoImpl implements TechStackDao {

    private final JdbcTemplate jdbcTemplate;

    public TechStackDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> TechStack.builder()
            .id(rs.getLong("id"))
            .profileId(rs.getLong("profile_id"))
            .name(rs.getString("name"))
            .category(Category.valueOf(rs.getString("category")))
            .proficiency(Proficiency.valueOf(rs.getString("proficiency")))
            .yearsOfExp(rs.getInt("years_of_exp"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
            .build();

    @Override
    public TechStack save(TechStack techStack) {
        String sql = "INSERT INTO tech_stack (profile_id, name, category, proficiency, years_of_exp) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, techStack.getProfileId());
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getCategory().name());
            ps.setString(4, techStack.getProficiency().name());
            ps.setInt(5, techStack.getYearsOfExp());
            return ps;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("ID")).longValue();
        return findById(generatedId).orElseThrow();
    }

    @Override
    public Optional<TechStack> findById(Long id) {
        String sql = "SELECT * FROM tech_stack WHERE id = ?";
        List<TechStack> results = jdbcTemplate.query(sql, techStackRowMapper, id);
        return results.stream().findFirst();
    }

    @Override
    public List<TechStack> findByProfileId(Long profileId) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ?";
        return jdbcTemplate.query(sql, techStackRowMapper, profileId);
    }

    @Override
    public TechStack update(TechStack techStack) {
        String sql = "UPDATE tech_stack SET name = ?, category = ?, proficiency = ?, years_of_exp = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getCategory().name(),
                techStack.getProficiency().name(),
                techStack.getYearsOfExp(),
                techStack.getId());
        return findById(techStack.getId()).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tech_stack WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<TechStack> findByProfileId(Long profileId, int offset, int size) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, techStackRowMapper, profileId, size, offset);
    }

    @Override
    public long countByProfileId(Long profileId) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, profileId);
    }

    @Override
    public List<TechStack> findByProfileIdAndCategory(Long profileId, String category, int offset, int size) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? AND category = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, techStackRowMapper, profileId, category, size, offset);
    }

    @Override
    public long countByProfileIdAndCategory(Long profileId, String category) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ? AND category = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, profileId, category);
    }

    @Override
    public List<TechStack> findByProfileIdAndProficiency(Long profileId, String proficiency, int offset, int size) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? AND proficiency = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, techStackRowMapper, profileId, proficiency, size, offset);
    }

    @Override
    public long countByProfileIdAndProficiency(Long profileId, String proficiency) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ? AND proficiency = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, profileId, proficiency);
    }
}
