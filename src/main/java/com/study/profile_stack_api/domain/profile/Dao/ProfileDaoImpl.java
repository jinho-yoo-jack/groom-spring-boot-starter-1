package com.study.profile_stack_api.domain.profile.Dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
@Primary
@RequiredArgsConstructor
public class ProfileDaoImpl implements ProfileDao {

    private final JdbcTemplate jdbcTemplate;

    // ============== CREATE =================
    @Override
    public Profile save(Profile profile) {
        String sql = """
                INSERT INTO profile (name, email, bio, position, career_years, github_url, blog_url, created_at) 
                VALUES (?, ?, ?, ?, ?, ? ,?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getPosition().name());
            ps.setInt(5, profile.getCareerYears());
            ps.setString(6, profile.getGithubUrl());
            ps.setString(7, profile.getBlogUrl());
            ps.setTimestamp(8, Timestamp.valueOf(profile.getCreatedAt()));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            profile.setId(generatedId.longValue());
        }

        return profile;
    }
}
