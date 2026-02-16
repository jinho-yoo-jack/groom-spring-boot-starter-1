package com.study.profile_stack_api.domain.techstack.dao;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

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
}
