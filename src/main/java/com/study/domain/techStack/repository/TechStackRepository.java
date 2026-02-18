package com.study.domain.techStack.repository;

import com.study.domain.techStack.dao.TechStackDao;
import com.study.domain.techStack.entity.TechStack;
import com.study.global.common.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TechStackRepository {

    private final TechStackDao techStackDao;

    public TechStackRepository(TechStackDao techStackDao) {
        this.techStackDao = techStackDao;
    }

    public TechStack save(TechStack techStack) {
        return techStackDao.save(techStack);
    }

    public Page<TechStack> findAllByProfileIdWithPaging(Long profileId, int page, int size) {
        return techStackDao.findAllByProfileIdWithPaging(profileId, page, size);
    }

    public Optional<TechStack> findById(Long profileId, Long id) {
        return techStackDao.findById(profileId, id);
    }

    public TechStack update(TechStack techStack) {
        return techStackDao.update(techStack);
    }

    public boolean deleteById(Long profileId, Long id) {
        return techStackDao.deleteById(profileId, id);
    }

    public boolean existsById(Long profileId, Long id) {
        return techStackDao.existsById(profileId, id);
    }
}
