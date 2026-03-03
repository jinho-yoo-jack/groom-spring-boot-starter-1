package com.study.profile_stack_api.domain.profile.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.global.exception.ProfileNotFoundException;

@Repository
public class ProfileRepository {
    private final ProfileDao profileDao;

    public ProfileRepository(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public Profile save(Profile profile) {
        return profileDao.save(profile);
    }

    public List<Profile> findAll(int page, int size) {
        int offset = page * size;
        return profileDao.findAll(offset, size);
    }

    public Profile findById(Long id) {
        return profileDao.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
    }

    public void deleteById(Long id) {
        profileDao.deleteById(id);
    }

    public Profile update(Profile profile) {
        return profileDao.update(profile);
    }

    public long count() {
        return profileDao.count();
    }

    public boolean existsByEmail(String email) {
        return profileDao.existsByEmail(email);
    }

    public List<Profile> findByPosition(String position, int page, int size) {
        int offset = page * size;
        return profileDao.findByPosition(position, offset, size);
    }

    public long countByPosition(String position) {
        return profileDao.countByPosition(position);
    }

    public List<Profile> findByName(String name, int page, int size) {
        int offset = page * size;
        return profileDao.findByName(name, offset, size);
    }

    public long countByName(String name) {
        return profileDao.countByName(name);
    }
}