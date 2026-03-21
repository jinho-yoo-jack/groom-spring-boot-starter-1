package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.global.common.Page;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDaoImpl implements ProfileDao {
    private final JdbcTemplate jdbcTemplate;

    public ProfileDaoImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Deprecated
    @Override
    public Profile save(Profile profile) {
        String sql = """
                insert into profile
                (name, email, bio, position, career_years, github_url, blog_url)
                values (?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, profile.getName());
                ps.setString(2, profile.getEmail());
                ps.setString(3, profile.getBio());
                ps.setString(4, profile.getPosition().name());
                ps.setInt(5, profile.getCareerYears());
                ps.setString(6, profile.getGithubUrl());
                ps.setString(7, profile.getBlogUrl());
                return ps;
            }, keyHolder);

        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("이미 존재하는 이메일입니다.");
        }

        Number generatedId = keyHolder.getKey();

        if(generatedId != null) {
            profile.setId(generatedId.longValue());
        }

        return profile;
    }

    @Deprecated
    @Override
    public Optional<Profile> findById(Long id) {
        String sql = """
                select *
                from profile
                where id = ?
                """;
        try {
            Profile profile = jdbcTemplate.queryForObject(sql, profileRowMapper, id);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Deprecated
    @Override
    public List<Profile> findByPosition(String position) {
        String sql = """
                select *
                from profile
                where position = ?
                """;

        return jdbcTemplate.query(sql, profileRowMapper, position);
    }

    @Deprecated
    @Override
    public Profile update(Profile profile) {
        String sql = """
                update profile
                set name = ?, email = ?, bio = ?,
                position = ?, career_years = ?,
                github_url = ?, blog_url = ?
                where id = ?
                """;
        int updated = jdbcTemplate.update(sql,
                profile.getName(),
                profile.getEmail(),
                profile.getBio(),
                profile.getPosition().name(),
                profile.getCareerYears(),
                profile.getGithubUrl(),
                profile.getBlogUrl(),
                profile.getId());

        if (updated == 0) {
            throw new ResourceNotFoundException(profile.getId());
        }

        profile.setUpdatedAt(LocalDateTime.now());
        return profile;
    }

    @Deprecated
    @Override
    public boolean deleteById(Long id) {
        String sql = "delete from profile where id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }

    @Deprecated
    @Override
    public boolean existById(Long id) {
        String sql = "select count(*) from profile where id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public Page<Profile> findAllWithPaging(int page, int size) {
        String countSql = "select count(*) from profile";
        Long totalElements = jdbcTemplate.queryForObject(countSql, Long.class);

        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        String dataSql = """
                select *
                from profile
                order by id desc
                limit ? offset ?
                """;

        int offset = page * size;
        List<Profile> content = jdbcTemplate.query(dataSql, profileRowMapper, size, offset);

        return new Page<>(content, page, size, totalElements);
    }

    private final RowMapper<Profile> profileRowMapper = (rs, rowNum) -> {
        Profile profile = new Profile();
        profile.setId(rs.getLong("id"));
        profile.setName(rs.getString("name"));
        profile.setEmail(rs.getString("email"));
        profile.setBio(rs.getString("bio"));
        profile.setPosition(Position.valueOf(rs.getString("position")));
        profile.setCareerYears(rs.getInt("career_years"));
        profile.setGithubUrl(rs.getString("github_url"));
        profile.setBlogUrl(rs.getString("blog_url"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return profile;
    };
}
