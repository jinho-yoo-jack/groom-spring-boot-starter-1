package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
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
public class ProfileDaoImpl implements ProfileDao {
    private final JdbcTemplate jdbcTemplate;

    public ProfileDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Profile save(Profile profile) {
        String sql = """          
        INSERT INTO profile (name, email, bio, position, career_years, github_url, blog_url, created_at, updated_at) 
        VALUES(?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
        """ ;

        //키홀더 : auto-generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getEmail());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getPosition());
            ps.setInt(5, profile.getCareerYears());
            ps.setString(6, profile.getGithubUrl());
            ps.setString(7, profile.getBlogUrl());
            return ps;
        }, keyHolder);

        Number gemeratedId = keyHolder.getKey();
        if(gemeratedId != null){
            profile.setId(gemeratedId.longValue());
        }

        return profile;
    }

    @Override
    public boolean existByEmail(String email){
        String sql = "SELECT COUNT(*) FROM profile WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        try {
            Profile profile = jdbcTemplate.queryForObject(sql, profileRowMapper, id);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            // Return Optional.empty() when no data exists as exception occurs
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findByPosition(String position){
        String sql = "SELECT * FROM profile WHERE position = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, profileRowMapper, position);
    }


    @Override
    public List<Profile> findAll() {
        String sql = "SELECT * FROM profile ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, profileRowMapper);
    }

    @Override
    public Profile update(Long id, Profile profile) {
        String sql = """
                UPDATE profile
                SET name=?, email=?, bio=?, position=?, career_years=?, github_url=?, blog_url=?, updated_at = NOW() 
                WHERE id=?
                """;

        jdbcTemplate.update(sql,
        profile.getName(),
        profile.getEmail(),
        profile.getBio(),
        profile.getPosition(),
        profile.getCareerYears(),
        profile.getGithubUrl(),
        profile.getBlogUrl(),
        profile.getId());

        return profile;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM profile WHERE id = ?";
        // 영향을 받은 행의 개수를 반환 (지워졌으면 1, 없어서 안 지워졌으면 0)
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0; // 0보다 크면 삭제 성공(true)
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM profile WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public Page<Profile> findAllwithPaging(int page, int size) {
        //  전체 건수 조회
        Long totalElements = count();
        // 전체 데이터가 0건이면 빈 페이지 반환
        if (totalElements == null || totalElements == 0) {
            return new Page<>(List.of(), page, size, 0);
        }

        //해당 페이지 데이터 조회
        String dataSql = "SELECT * FROM profile ORDER BY created_at DESC LIMIT ? OFFSET ?";

        int offset = page  * size;
        List<Profile> content = jdbcTemplate.query(dataSql, profileRowMapper, size, offset);

        //페이지 객체 생성 및 반환
        return new Page<>(content, page, size, totalElements);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM profile";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;

    }


    private final RowMapper<Profile> profileRowMapper = (rs, rowNum) -> {
      Profile profile = new Profile();
      profile.setId(rs.getLong("id"));
      profile.setName(rs.getString("name"));
      profile.setEmail(rs.getString("email"));
      profile.setBio(rs.getString("bio"));
      profile.setPosition(rs.getString("position"));
      profile.setCareerYears(rs.getInt("career_years"));
      profile.setGithubUrl(rs.getString("github_url"));
      profile.setBlogUrl(rs.getString("blog_url"));
      profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

      return profile;
    };

}
