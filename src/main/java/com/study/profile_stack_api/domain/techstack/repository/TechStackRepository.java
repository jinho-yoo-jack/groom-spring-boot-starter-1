package com.study.profile_stack_api.domain.techstack.repository;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
}
