package com.study.profile_stack_api.domain.profile.repository.impl;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.dao.ProfileDao;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MySQLStudyLogDaoImpl implements ProfileDao {
    // DB 접근을 도와주는 jdbc 유틸
    private final JdbcTemplate jdbcTemplate;
    public MySQLStudyLogDaoImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    // === Create ===
    @Override
    public Profile save(Profile profile) {
        String sql = "insert into profile(name, email, bio, position, career_years, github_url, blog_url, created_at, updated_at) values(?,?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setString(1,profile.getName());
            ps.setString(2,profile.getEmail());
            ps.setString(3,profile.getBio());
            ps.setString(4,profile.getPosition().name());
            ps.setInt(5,profile.getCareerYears());
            ps.setString(6,profile.getGithubUrl());
            ps.setString(7,profile.getBlogUrl());
            ps.setTimestamp(8, Timestamp.valueOf(profile.getCreatedAt()));
            ps.setTimestamp(9, Timestamp.valueOf(profile.getUpdatedAt()));
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if(generatedId != null){
            profile.setId(generatedId.longValue());
        }

        return profile;
    }

    // === Read ===
    @Override
    public Optional<Profile> findById(Long id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        try {
            Profile profile = jdbcTemplate.queryForObject(sql, (row, index) -> {
                Profile item = new Profile();
                item.setId(row.getLong("id"));
                item.setName(row.getString("name"));
                item.setEmail(row.getString("email"));
                item.setBio(row.getString("bio"));
                String position = row.getString("position");
                item.setPosition(Position.valueOf(position));
                item.setCareerYears(row.getInt("career_years"));
                item.setGithubUrl(row.getString("github_url"));
                item.setBlogUrl(row.getString("blog_url"));
                item.setCreatedAt(row.getTimestamp("created_at").toLocalDateTime());
                item.setUpdatedAt(row.getTimestamp("updated_at").toLocalDateTime());
                return item;
            }, id);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findWithPage(int offset, int limit) {
        return List.of();
    }

    @Override
    public List<Profile> findByPosition(String position) {
        return List.of();
    }


    // === Update ===
    @Override
    public Optional<Profile> update(Profile profile) {
        return Optional.empty();
    }


    // === Delete ===
    @Override
    public boolean deleteById(Long id) {
        return false;
    }


    // === Utils ===
    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "select count(*) from profile where email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;}
}
