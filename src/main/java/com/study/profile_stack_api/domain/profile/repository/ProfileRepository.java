package com.study.profile_stack_api.domain.profile.repository;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByPosition(String position);
}
