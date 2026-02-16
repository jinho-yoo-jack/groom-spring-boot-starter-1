package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class MySQLTechStackDaoImpl implements TechStackDao {
    public final JdbcTemplate jdbcTemplate;

    public MySQLTechStackDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==================== CREATE ====================

    @Override
    public TechStack saveByProfileId(Long profileId, TechStack techStack) {
        if (!existsById(profileId)) {
            throw new ProfileNotFoundException(profileId);
        }

        String sql = """
                INSERT INTO tech_stack (profile_id, name, category, proficiency, years_of_exp)
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, profileId);
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getCategory().name());
            ps.setString(4, techStack.getProficiency().name());
            ps.setInt(5, techStack.getYearsOfExp());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            techStack.setId(generatedId.longValue());
        }

        return techStack;
    }

    // ==================== READ ====================

    @Override
    public List<TechStack> findAllByProfileId(Long profileId) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? ORDER BY created_at DESC, id DESC";
        return jdbcTemplate.query(sql, techStackRowMapper, profileId);
    }

    @Override
    public Optional<TechStack> findByProfileIdAndId(Long profileId, Long id) {
        String sql = "SELECT *FROM tech_stack WHERE profile_id = ? AND id = ?";

        try {
            TechStack techStack = jdbcTemplate.queryForObject(sql, techStackRowMapper, profileId, id);
            return Optional.ofNullable(techStack);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // ==================== UPDATE ====================

    @Override
    public TechStack updateByProfileId(Long profileId, TechStack techStack) {
        String sql = """
                UPDATE tech_stack
                SET name = ?, category = ?, proficiency = ?, years_of_exp = ?
                WHERE profile_id = ? AND id = ?
                """;

        int updated = jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getCategory().name(),
                techStack.getProficiency().name(),
                techStack.getYearsOfExp(),
                profileId,
                techStack.getId());

        if (updated == 0) {
            throw new TechStackNotFoundException(techStack.getId());
        }

        return techStack;
    }

    // ==================== DELETE ====================

    @Override
    public boolean deleteByProfileIdAndId(Long profileId, Long id) {
        String sql = "DELETE FROM tech_stack WHERE profile_id = ? AND id = ?";
        int deleted = jdbcTemplate.update(sql, profileId, id);
        return deleted > 0;
    }

    @Override
    public int deleteAllByProfileId(Long profileId) {
        String sql = "DELETE FROM tech_stack WHERE profile_id = ?";
        return jdbcTemplate.update(sql, profileId);
    }

    // ==================== VALIDATION ====================

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM profile WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    // ==================== PRIVATE METHODS ====================

    /**
     * ResultSet의 각 행을 TechStack 객체로 변환
     */
    private final RowMapper<TechStack> techStackRowMapper = (rs, rowMapper) -> {
        TechStack techStack = new TechStack();
        techStack.setId(rs.getLong("id"));
        techStack.setProfileId(rs.getLong("profile_id"));
        techStack.setName(rs.getString("name"));
        techStack.setCategory(TechCategory.valueOf(rs.getString("category")));
        techStack.setProficiency(Proficiency.valueOf(rs.getString("proficiency")));
        techStack.setYearsOfExp(rs.getInt("years_of_exp"));
        techStack.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        techStack.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return techStack;
    };
}
