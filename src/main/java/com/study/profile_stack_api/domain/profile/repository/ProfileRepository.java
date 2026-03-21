package com.study.profile_stack_api.domain.profile.repository;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select p from Profile p where p.id = :id")
    Optional<Profile> findByIdOptimized(@Param("id") Long id);

    List<Profile> findByPosition(Position position);
}
