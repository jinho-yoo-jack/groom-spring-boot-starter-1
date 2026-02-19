package com.study.profile_stack_api.domain.techstack.dao;


import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository// 스프링 빈으로 등록

public class TechStackDaoImpl implements TechStackDao {
    private final JdbcTemplate jdbcTemplate;

    public TechStackDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public TechStack savetechstack(TechStack techStack){
        String sql = """
                INSERT INTO tech_stack (profile_id, name, category, proficiency, years_of_exp, created_at, updated_at) 
                VALUES (?, ?, ?, ?, ?, NOW(), NOW())
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, techStack.getProfileId());
            ps.setString(2, techStack.getName());
            ps.setString(3, techStack.getCategory().name());
            ps.setString(4, techStack.getProficiency().name());
            ps.setInt(5, techStack.getYearsOfExp());
            return ps;
        }, keyHolder);

        Number gemeratedId = keyHolder.getKey();
        if(gemeratedId != null){
            techStack.setId(gemeratedId.longValue());
        }

        return techStack;
    }

    @Override
    public long countByProfileId(Long profileId) {
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE profile_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, profileId);
        return count != null ? count : 0L;
    }

    @Override
    public Page<TechStack> getAllTechStacksByProfileId(Long profileId, int page, int size) {
        //  전체 건수 조회
        Long totalElements = countByProfileId(profileId);
        // 전체 데이터가 0건이면 빈 페이지 반환
        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        //페이징 쿼리
        String sql = """
            SELECT * FROM tech_stack 
            WHERE profile_id = ? 
            ORDER BY created_at DESC 
            LIMIT ? OFFSET ?
            """;

        //OFFSET 계산
        int offset = page * size;

        List<TechStack> content = jdbcTemplate.query(
                sql,
                techStackRowMapper,
                profileId, size, offset
        );

        //페이지 반환
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public Optional<TechStack> getTechStackByIdAndProfileId(Long id, Long profileId) {

        String sql = "SELECT * FROM tech_stack WHERE id = ? and profile_id = ?";
        try {
            TechStack profile = jdbcTemplate.queryForObject(sql, techStackRowMapper, id, profileId);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            // Return Optional.empty() when no data exists as exception occurs
            return Optional.empty();
        }

    }

    @Override
    public TechStack updateTechStack(Long id, Long profileId,  TechStack techStack) {
        String sql = """
            UPDATE tech_stack 
            SET name = ?, 
                category = ?, 
                proficiency = ?, 
                years_of_exp = ?, 
                updated_at = NOW()
            WHERE id = ? AND profile_id = ?
            """;

        jdbcTemplate.update(sql,
                techStack.getName(),
                techStack.getCategory().name(),    // Enum을 문자열로 변환
                techStack.getProficiency().name(), // Enum을 문자열로 변환
                techStack.getYearsOfExp(),
                id,
                profileId);

        // 업데이트된 객체를 그대로 반환 (Service에서 응답값으로 써야 하니까요)
        return techStack;
    }

    @Override
    public boolean deleteTechStack(Long id, Long profileId) {
        String sql = "DELETE FROM tech_stack WHERE id = ? AND profile_id = ?";
        int affectedRows = jdbcTemplate.update(sql, id, profileId);

        // 삭제된 행이 1개 이상이면 true, 아니면 false 반환
        return affectedRows > 0;
    }

    @Override
    public boolean existById(Long id){
        String sql = "SELECT COUNT(*) FROM tech_stack WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }


    private final RowMapper<TechStack> techStackRowMapper = (rs, rowNum) -> {
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
