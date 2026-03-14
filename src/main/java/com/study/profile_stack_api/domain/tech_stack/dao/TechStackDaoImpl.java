package com.study.profile_stack_api.domain.tech_stack.dao;

import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
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
public class TechStackDaoImpl implements TechStackDao{

    private final JdbcTemplate jdbcTemplate;

    public TechStackDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TechStack save(TechStack techStack) {
        String sql = """
                insert into tech_stack
                (profile_id, name, category, proficiency, years_of_exp)
                values (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, techStack.getProfileId());
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

    @Override
    public List<TechStack> findAll(Long profileId) {
        String sql = """
                select *
                from tech_stack
                where profile_id = ?
                order by id desc
                """;

        return jdbcTemplate.query(sql, techStackRowMapper, profileId);
    }

    @Override
    public Optional<TechStack> findTechStackById(Long profileId ,Long id) {
        String sql = """
                select *
                from tech_stack
                where id = ?
                """;

        try {
            TechStack techStack = jdbcTemplate.queryForObject(sql, techStackRowMapper, profileId, id);
            return Optional.ofNullable(techStack);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public TechStack update(TechStack techStack) {
        // TODO: RowNumber 적용하는 방법을 알아내고 수정할 것
        // 일단 임시로 ID를 이용하게끔 설정
        String sql = """
                update tech_stack
                set name = ?, category = ?, proficiency = ?,
                years_of_exp = ?
                where id = ?
                """;

        int updated = jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getCategory().name(),
                techStack.getProficiency().name(),
                techStack.getYearsOfExp(),
                techStack.getId());

        if (updated == 0) {
            throw new RuntimeException("TechStack not found: " + techStack.getId());
        }
        return techStack;
    }

    @Override
    public boolean exitsById(Long id) {
        String sql = """
                select count(*)
                from tech_stack
                where id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = """
                delete 
                from tech_stack
                where id = ?
                """;
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }

    public RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
        TechStack techStack = new TechStack();
        techStack.setId(rs.getLong("id"));
        techStack.setProfileId(rs.getLong("profile_id"));
        techStack.setName(rs.getString("name"));
        techStack.setCategory(Category.valueOf(rs.getString("category")));
        techStack.setProficiency(Proficiency.valueOf(rs.getString("proficiency")));
        techStack.setYearsOfExp(rs.getInt("years_of_exp"));
        techStack.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        techStack.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return techStack;
    };
}
