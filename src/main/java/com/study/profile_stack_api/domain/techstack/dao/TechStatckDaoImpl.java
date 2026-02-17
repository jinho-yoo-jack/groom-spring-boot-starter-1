package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TechStatckDaoImpl implements TechStackDao {

    private final JdbcTemplate jdbcTemplate;

    //============== CREATE ===================
    @Override
    public TechStack save(TechStack techStack ) {
        String sql = """
                INSERT INTO tech_stack (profile_id, name, category, proficiency, years_of_exp,created_at,updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setLong(1, techStack.getProfileId());
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getTechCategory().getDescription());
            ps.setString(4, techStack.getProficency().getDescription());
            ps.setInt(5, techStack.getYearsOfExp());
            ps.setTimestamp(6, Timestamp.valueOf(techStack.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(techStack.getUpdatedAt()));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            techStack.setId(generatedId.longValue());
        }

        return techStack;
    }

    //============== READ ===================

    @Override
    public Page<TechStack> getAllTechStacks(long profileId, int page, int size, String category, String proficiency) {
        List<Object> countParams = new ArrayList<>();
        List<Object> dataParams = new ArrayList<>();

        String countSql = "SELECT count(*) FROM tech_stack WHERE 1=1 AND profile_id = ?";
        countParams.add(profileId);

        if (category != null) {
            countSql += " AND category = ?";
            countParams.add(category);
        }

        if (proficiency != null) {
            countSql += " AND proficiency LIKE ?";
            countParams.add("%" + proficiency + "%");
        }

        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, countParams.toArray());

        // 전체 데이터가 0건이면 빈 페이지 반환
        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String datasql = """
                SELECT * FROM tech_stack
                WHERE profile_id = ?
                """;
        dataParams.add(profileId);

        if (category != null) {
            datasql += " AND category = ?";
            dataParams.add(category);
        }

        if (proficiency != null) {
            datasql += " AND proficiency = ?";
            dataParams.add(proficiency);
        }

        datasql += " ORDER BY id DESC LIMIT ? OFFSET ?";

        int offset = page * size;
        dataParams.add(size);
        dataParams.add(offset);
        List<TechStack> content = jdbcTemplate.query(datasql, techStackRowMapper, dataParams.toArray());

        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<TechStack> getTechStack(long id) {
        String sql = "SELECT * FROM tech_stack WHERE id = ?";

        try {
            TechStack techStack = jdbcTemplate.queryForObject(sql, techStackRowMapper, id);
            return Optional.ofNullable(techStack);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //============== READ ===================

    @Override
    public TechStack updateTechStack(long profileId, long id, TechStack techStack) {
        String sql = """
                UPDATE tech_stack
                SET name = ?, category = ?, proficiency = ?, years_of_exp = ?
                WHERE profile_id = ? and id = ?
                """;

        int updated = jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getTechCategory().getDescription(),
                techStack.getProficency().getDescription(),
                techStack.getYearsOfExp(),
                profileId,
                id);

        if (updated == 0) {
            throw new RuntimeException("techStack not found. ID: " + techStack.getId());
        }

        return techStack;
    }

    //============== DELETE ===================


    @Override
    public void deleteTechStackById(long id) {
        String sql = "DELETE FROM tech_stack WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    // ===================================
    private final RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
        TechStack techStack = new TechStack();
        techStack.setId(rs.getLong("id"));
        techStack.setProfileId(rs.getLong("profile_id"));
        techStack.setName(rs.getString("name"));
        techStack.setTechCategory(TechCategory.valueOf(rs.getString("category")));
        techStack.setProficency(Proficiency.valueOf(rs.getString("proficiency")));
        techStack.setYearsOfExp(rs.getInt("years_of_exp"));
        techStack.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        techStack.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return techStack;
    };
}
