package com.study.profile_stack_api.domain.profile.repository;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 프로필 저장소
 */
@Repository
public class ProfileRepository {
    /** 데이터 저장소 */
    private final Map<Long, Profile> database = new HashMap<>();

    /** ID 자동 증가 시퀀스 */
    private final AtomicLong sequence = new AtomicLong(1);

    // ==================== CREATE ====================

    /**
     * 프로필 저장
     *
     * @param profile 저장할 프로필
     * @return 저장된 프로필 (ID 포함)
     */
    public Profile save(Profile profile) {
        // ID 없는 경우 생성
        if (profile.getId() == null) {
            profile.setId(sequence.getAndIncrement());
        }
        database.put(profile.getId(), profile);
        return profile;
    }

    // ==================== VALIDATION ====================

    public boolean existsByEmail(String email) {
        return database.values().stream()
                .anyMatch(profile -> profile.getEmail() != null
                        && profile.getEmail().equalsIgnoreCase(email));
    }

    // ==================== LIFECYCLE CALLBACK ====================

    @PostConstruct
    public void init() {
        System.out.println("========================================");
        System.out.println("🚀 ProfileRepository 초기화 완료!");
        System.out.println(" - 데이터 저장소(MAP) 준비됨");
        System.out.println(" - ID 생성기 준비됨");
        System.out.println("========================================");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("========================================");
        System.out.println("🔚 ProfileRepository 종료!");
        System.out.println(" - 저장된 데이터 수: " + database.size() + "개");
        System.out.println(" - 마지막 ID: " + (sequence.get() - 1));
        System.out.println("========================================");
    }
}
