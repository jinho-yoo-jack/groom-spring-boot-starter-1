package com.study.profile_stack_api.domain.tech_stack.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;
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

    // TODO: RowNumber 적용하는 방법을 알아내고 수정할 것
    // 일단 임시로 ID를 이용하게끔 설정
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
            ps.setLong(1, techStack.getProfile().getId());
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
    public Optional<TechStack> findTechStackById(Long profileId, Long id) {
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
    public boolean existsById(Long id) {
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

    @Override
    public Page<TechStack> findAllWithPaging(Long profileId, int page, int size) {
        String countSql = """
                select count(*)
                from tech_stack
                where profile_id = ?
                """;

        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class, profileId);

        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String dataSql = """
                select *
                from tech_stack
                where profile_id = ?
                order by id desc
                limit ? offset ?
                """;

        int offset = page * size;
        List<TechStack> content = jdbcTemplate.query(dataSql, techStackRowMapper, profileId, size, offset);

        return new Page<>(content, page, size, totalElements);
    }

    public RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
        TechStack techStack = new TechStack();
        Profile profileStub = new Profile();

        profileStub.setId(rs.getLong("profile_id"));
        techStack.setId(rs.getLong("id"));
        techStack.setProfile(profileStub);
        techStack.setName(rs.getString("name"));
        techStack.setCategory(Category.valueOf(rs.getString("category")));
        techStack.setProficiency(Proficiency.valueOf(rs.getString("proficiency")));
        techStack.setYearsOfExp(rs.getInt("years_of_exp"));
        techStack.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        techStack.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return techStack;
    };
}
