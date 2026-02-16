package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.techstack.dao.TechStackDao;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackDao techstackDao;
    private final ProfileDao profileDao;

    //=============== CREATE ====================
    public TechStackResponse save(long profileId, TechStackRequest techstackRequest) {
        profileDao.getProfile(profileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로필을 찾을 수 없습니다. (id : " + profileId + ")"));

        TechStack techStack = new TechStack(techstackRequest);
        techStack.setProfileId(profileId);

        return TechStackResponse.from(techstackDao.save(techStack));
    }
}
