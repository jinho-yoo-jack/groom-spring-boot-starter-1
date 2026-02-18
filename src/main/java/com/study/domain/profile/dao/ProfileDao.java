package com.study.domain.profile.dao;

import com.study.domain.profile.entity.Profile;
import com.study.global.common.Page;

import java.util.List;
import java.util.Optional;

/**
 * Profile DAO 인터페이스
 * <p>
 * DAO(Data Access Object) 패턴:
 * - 데이터베이스 접근 로직을 캡슐화
 * - 비즈니스 로직과 데이터 접근 로직을 분리
 * - 데이터베이스 종류에 독립적인 인터페이스 제공
 */
public interface ProfileDao {

    // ========== CREATE ==========
    Profile save(Profile profile);

    // ========== READ ==========
    Optional<Profile> findById(Long id);

    List<Profile> findAll();

    /**
     * 직무(포지션)별 조회
     * @param position "BACKEND", "FRONTEND" 등 (문자열 기준)
     */
    List<Profile> findByPosition(String position);

    /**
     * 이메일로 조회 (중복 체크 등에 활용)
     */
    Optional<Profile> findByEmail(String email);

    // ========== UPDATE ==========
    Profile update(Profile profile);

    // ========== DELETE ==========
    boolean deleteById(Long id);

    boolean existsById(Long id);

    /**
     * 전체 삭제 (테스트/초기화 용도)
     */
    void deleteAll();

    // ========== PAGING ==========
    Page<Profile> findAllWithPaging(int page, int size);

    Page<Profile> findByPositionWithPaging(String position, int page, int size);

    /**
     * 검색 조건과 함께 페이징하여 조회
     * - 이름 키워드 검색
     * - 포지션 필터
     * - 경력 범위 필터
     *
     * @param nameKeyword 이름 검색 키워드 (null 허용)
     * @param position 포지션 (null 허용)
     * @param minCareerYears 최소 경력 (null 허용)
     * @param maxCareerYears 최대 경력 (null 허용)
     */
    Page<Profile> searchWithPaging(
            String nameKeyword,
            String position,
            Integer minCareerYears,
            Integer maxCareerYears,
            int page, int size
    );

    // ========== COUNT ==========
    long count();

    long countByPosition(String position);

    long countByEmail(String email);
}
