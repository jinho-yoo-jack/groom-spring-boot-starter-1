package com.study.profile_stack_api.domain.techstack.repository;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
     * 기술 스택 저장
     *
     * @param techStack 저장할 기술 스택
     * @return 저장된 기술 스댁 (ID 포함)
     */
    public TechStack save(TechStack techStack) {
        // ID 없는 경우 생성
        if (techStack.getId() == null) {
            techStack.setId(sequence.getAndIncrement());
        }
        database.put(techStack.getId(), techStack);
        return techStack;
    }
}
