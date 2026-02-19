package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.PageResponse;
import com.study.profile_stack_api.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TechStackDaoImpl implements TechStackDao{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public TechStack saveTechStack(TechStack techStack) {
        String sql = """
                INSERT INTO tech_stack(profile_id, name, category, proficiency, years_of_exp) VALUES(?,?,?,?,?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, techStack.getProfileId());
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getCategory());
            ps.setString(4, techStack.getProficiency());
            ps.setInt(5, techStack.getYearsOfExp());
            return ps;

        }, keyHolder);

        Number generatedId = (Number) keyHolder.getKeys().get("ID");

        if(generatedId != null)
            techStack.setId(generatedId.longValue());

        return techStack;
    }

    @Override
    public PageResponse<TechStack> findAllWithPaging(int page, int size, Long profileId) {
        String countSql = """
                SELECT COUNT(*) FROM tech_stack WHERE profile_id = ?
                """;
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, profileId);

        if(totalElements == null || totalElements == 0)
            return new PageResponse<>(List.of(), page, size, 0);

        String dataSql = """
                SELECT * FROM tech_stack WHERE profile_id = ? LIMIT ? OFFSET ?
                """;
        int offset = page * size;
        List<TechStack> content = jdbcTemplate.query(dataSql, techStackRowMapper, profileId, size, offset);

        return new PageResponse<>(content, page, size, totalElements);
    }

    @Override
    public Optional<TechStack> findById(Long profileId, Long id) {
        String sql = "SELECT * FROM tech_stack WHERE profile_id = ? AND id = ?";

        try{
            TechStack techStack = jdbcTemplate.queryForObject(sql, techStackRowMapper, profileId, id);
            return Optional.ofNullable(techStack);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TechStack updateTechStack(Long profileId, Long id, TechStack techStack) {
        String sql = """
                UPDATE tech_stack SET name = ?, category = ?, proficiency = ?, years_of_exp = ? WHERE id = ? AND profile_id = ?
                """;

        int updated = jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getCategory(),
                techStack.getProficiency(),
                techStack.getYearsOfExp(),
                id,
                profileId
                );
        if(updated == 0)
            throw new DataNotFoundException("기술 스택", id);

        return techStack;
    }

    @Override
    public void deleteTechStack(Long profileId, Long id) {
        String sql = "DELETE FROM tech_stack WHERE id = ? AND profile_id = ?";
        int deleted = jdbcTemplate.update(sql, id, profileId);

        if (deleted == 0) {
            throw new DataNotFoundException("기술 스택", id);
        }
    }

    private final RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
        TechStack techStack = TechStack.builder()
                .id(rs.getLong("id"))
                .profileId(rs.getLong("profile_id"))
                .name(rs.getString("name"))
                .category(rs.getString("category"))
                .proficiency(rs.getString("proficiency"))
                .yearsOfExp(rs.getInt("years_of_exp"))
                .build();

        return techStack;
    };

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM tech_stack WHERE id = ?) ";

        try {
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return result != null && result == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
