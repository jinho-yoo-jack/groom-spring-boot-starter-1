package com.study.domain.profile.repository;

import com.study.domain.profile.dao.ProfileDao;
import com.study.domain.profile.entity.Profile;
import com.study.global.common.Page;

import java.util.Optional;

public class ProfileRepository {
    private final ProfileDao profileDao;

    public ProfileRepository(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    // CREATE
    public Profile save(Profile profile) {
        return profileDao.save(profile);
    }

    // READ
    public Optional<Profile> findById(Long id) {
        return profileDao.findById(id);
    }

    public Page<Profile> findAllWithPaging(int page, int size) {
        return profileDao.findAllWithPaging(page, size);
    }

    public Page<Profile> findByPositionWithPaging(String position, int page, int size) {
        return profileDao.findByPositionWithPaging(position, page, size);
    }

    public Optional<Profile> findByEmail(String email) {
        return profileDao.findByEmail(email);
    }

    public boolean existsById(Long id) {
        return profileDao.existsById(id);
    }

    public long countByEmail(String email) {
        return profileDao.countByEmail(email);
    }

    // UPDATE
    public Profile update(Profile profile) {
        return profileDao.update(profile);
    }

    // DELETE
    public boolean deleteById(Long id) {
        return profileDao.deleteById(id);
    }
}

