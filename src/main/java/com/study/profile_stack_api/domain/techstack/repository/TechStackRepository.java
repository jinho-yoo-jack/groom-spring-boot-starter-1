package com.study.profile_stack_api.domain.techstack.repository;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 기술 스택 저장소
 */
@Repository
public class TechStackRepository {
    /** 데이터 저장소 */
    private final Map<Long, TechStack> database = new HashMap<>();

    /** ID 자동 증가 시퀀스 */
    private final AtomicLong sequence = new AtomicLong(1);

    // ==================== CREATE ====================

    /**
     * 프로필별 기술 스택 저장
     *
     * @param techStack 저장할 기술 스택
     * @return 저장된 기술 스택 (ID 포함)
     */
    public TechStack save(TechStack techStack) {
        // ID 없는 경우 생성
        if (techStack.getId() == null) {
            techStack.setId(sequence.getAndIncrement());
        }
        database.put(techStack.getId(), techStack);
        return techStack;
    }

    // ==================== READ ====================

    /**
     * 프로필별 기술 스택 전체 조회 (최신순 정렬)
     *
     * @return 프로필별 기술 스택 전체 리스트
     */
    public List<TechStack> findAllByProfileId(Long profileId) {
        return database.values().stream()
                .filter(techStack -> techStack.getProfileId().equals(profileId))
                .sorted(Comparator.comparing(TechStack::getCreatedAt))
                .collect(Collectors.toList());
    }

    /**
     * 프로필별 기술 스택 ID로 단건 조회
     *
     * @param id 조회할 기술 스택 ID
     * @return 기술 스택 (없다면 Null)
     */
    public Optional<TechStack> findByProfileIdAndId(Long profileId, Long id) {
        return Optional.ofNullable(database.get(id))
                .filter(techStack -> techStack.getProfileId().equals(profileId));
    }

    // ==================== UPDATE ====================

    /**
     * 기술 스택 수정
     *
     * @param techStack 수정할 기술 스택
     * @return 기술 스택
     */
    public TechStack update(TechStack techStack) {
        if (techStack.getId() == null) {
            throw new IllegalArgumentException("수정할 기술 스택의 ID가 없습니다.");
        }
        if (!database.containsKey(techStack.getId())) {
            throw new TechStackNotFoundException(techStack.getId());
        }
        database.put(techStack.getId(), techStack);
        return techStack;
    }

    // ==================== LIFECYCLE CALLBACK ====================

    @PostConstruct
    public void init() {
        System.out.println("========================================");
        System.out.println("🚀 TechStackRepository 초기화 완료!");
        System.out.println(" - 데이터 저장소(MAP) 준비됨");
        System.out.println(" - ID 생성기 준비됨");
        System.out.println("========================================");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("========================================");
        System.out.println("🔚 TechStackRepository 정리 중...");
        System.out.println(" - 저장된 데이터 수: " + database.size() + "개");
        System.out.println(" - 마지막 ID: " + (sequence.get() - 1));
        database.clear();
        System.out.println(" - 데이터 정리 완료!");
        System.out.println("========================================");
    }
}
