package com.study.profile_stack_api.domain.techstack.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.study.profile_stack_api.domain.techstack.dao.TechStackDao;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.exception.TechStackNotFoundException;

@Repository
public class TechStackRepository {
    private final TechStackDao techStackDao;

    public TechStackRepository(TechStackDao techStackDao) {
        this.techStackDao = techStackDao;
    }

    public TechStack save(TechStack techStack) {
        return techStackDao.save(techStack);
    }

    public TechStack findById(Long id) {
        return techStackDao.findById(id)
                .orElseThrow(() -> new TechStackNotFoundException(id));
    }

    public List<TechStack> findByProfileId(Long profileId) {
        return techStackDao.findByProfileId(profileId);
    }

    public TechStack update(TechStack techStack) {
        return techStackDao.update(techStack);
    }

    public void deleteById(Long id) {
        techStackDao.deleteById(id);
    }

    public List<TechStack> findByProfileId(Long profileId, int page, int size) {
        int offset = page * size;
        return techStackDao.findByProfileId(profileId, offset, size);
    }

    public long countByProfileId(Long profileId) {
        return techStackDao.countByProfileId(profileId);
    }

    public List<TechStack> findByProfileIdAndCategory(Long profileId, String category, int page, int size) {
        int offset = page * size;
        return techStackDao.findByProfileIdAndCategory(profileId, category, offset, size);
    }

    public long countByProfileIdAndCategory(Long profileId, String category) {
        return techStackDao.countByProfileIdAndCategory(profileId, category);
    }

    public List<TechStack> findByProfileIdAndProficiency(Long profileId, String proficiency, int page, int size) {
        int offset = page * size;
        return techStackDao.findByProfileIdAndProficiency(profileId, proficiency, offset, size);
    }

    public long countByProfileIdAndProficiency(Long profileId, String proficiency) {
        return techStackDao.countByProfileIdAndProficiency(profileId, proficiency);
    }
}