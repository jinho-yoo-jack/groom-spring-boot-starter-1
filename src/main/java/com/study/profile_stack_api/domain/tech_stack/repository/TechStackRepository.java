package com.study.profile_stack_api.domain.tech_stack.repository;

import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    @Query("select t from TechStack t where t.id = :id and t.profile.id = :profileId")
    Optional<TechStack> findByIdOptimized(@Param("profileId") Long profileId, @Param("id") Long id);

    boolean existsById(Long id);

    boolean existsByIdAndProfileId(Long id, Long profileId);
}
