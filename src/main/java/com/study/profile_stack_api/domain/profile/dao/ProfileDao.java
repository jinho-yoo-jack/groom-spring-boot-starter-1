package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.common.Page;

import java.util.List;
import java.util.Optional;

// DAO 인터페이스
/**
 * @deprecated
 * 추후 Paging 또한 JPA 기반으로 구현될 시,
 * 해당 jdbc 기반 코드는 삭제할 예정.
 */
@Deprecated(forRemoval = false)
public interface ProfileDao {
    // Create
    Profile save(Profile profile);

    // Read
    Optional<Profile> findById(Long id);
    List<Profile> findByPosition(String position);

    // Update
    Profile update(Profile profile);

    // Delete
    boolean deleteById(Long id);

    boolean existById(Long id);

    // Paging
    // TODO : Paging 구현
    Page<Profile> findAllWithPaging(int page, int size);
}
