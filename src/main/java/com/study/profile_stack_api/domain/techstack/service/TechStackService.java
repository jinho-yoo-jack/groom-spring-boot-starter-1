package com.study.profile_stack_api.domain.techstack.service;

import com.study.profile_stack_api.domain.profile.dao.ProfileDao;
import com.study.profile_stack_api.domain.techstack.dao.TechStackDao;
import com.study.profile_stack_api.domain.techstack.dto.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.global.common.PageResponse;
import com.study.profile_stack_api.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final TechStackDao techStackDao;
    private final ProfileDao profileDao;

    public TechStackResponse addTechStack(Long profileId, TechStackRequest techStackRequest) {

        if (!profileDao.existsById(profileId)) {
            throw new DataNotFoundException("프로필", profileId);
        }

        validate(techStackRequest);

        TechCategory techCategory = TechCategory.of(techStackRequest.getCategory());
        Proficiency proficiency = Proficiency.of(techStackRequest.getProficiency());

        TechStack techStack = TechStack.builder()
                .profileId(profileId)
                .name(techStackRequest.getName())
                .category(techCategory.name())
                .proficiency(proficiency.name())
                .yearsOfExp(techStackRequest.getYearsOfExp())
                .build();

        TechStack savedTechStack = techStackDao.saveTechStack(techStack);

        return TechStackResponse.from(savedTechStack);
    }

    public PageResponse<TechStackResponse> findAllWithPaging(int page, int size, Long profileId) {

        if (!profileDao.existsById(profileId)) {
            throw new DataNotFoundException("프로필", profileId);
        }

        PageResponse<TechStack> techStackPage = techStackDao.findAllWithPaging(page, size, profileId);

        List<TechStackResponse> profileResponseList = techStackPage.getContent().stream()
                .map(TechStackResponse::from)
                .toList();

        return new PageResponse<>(
                profileResponseList,
                techStackPage.getPage(),
                techStackPage.getSize(),
                techStackPage.getTotalElements()
        );
    }

    public TechStackResponse getTechStack(Long profileId, Long id) {

        if (!profileDao.existsById(profileId)) {
            throw new DataNotFoundException("프로필", profileId);
        }

        TechStack techStack = techStackDao.findById(profileId, id).orElseThrow();

        return TechStackResponse.from(techStack);
    }

    public TechStackResponse updateTechStack(Long profileId, Long id, TechStackRequest techStackRequest) {

        if (!profileDao.existsById(profileId)) {
            throw new DataNotFoundException("프로필", profileId);
        }

        validate(techStackRequest);

        TechCategory techCategory = TechCategory.of(techStackRequest.getCategory());
        Proficiency proficiency = Proficiency.of(techStackRequest.getProficiency());

        TechStack techStack = techStackDao.findById(profileId, id)
                .orElseThrow(() -> new DataNotFoundException("기술 스택", id));

        techStack.setName(techStackRequest.getName());
        techStack.setCategory(techCategory.name());
        techStack.setProficiency(proficiency.name());
        techStack.setYearsOfExp(techStackRequest.getYearsOfExp());

        TechStack updatedTechStack = techStackDao.updateTechStack(profileId, id, techStack);

        return TechStackResponse.from(updatedTechStack);
    }

    public void deleteTechStack(Long profileId, Long id) {

        if (!profileDao.existsById(profileId)) {
            throw new DataNotFoundException("프로필", profileId);
        }

        if (!techStackDao.existsById(id)) {
            throw new DataNotFoundException("기술 스택", id);
        }

        techStackDao.deleteTechStack(profileId, id);
    }

    private void validate(TechStackRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수 입력 항목이며 공백일 수 없습니다.");
        }

        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("기술 스택은 필수 입력 항목이며 공백일 수 없습니다.");
        }

        if (request.getProficiency() == null || request.getProficiency().trim().isEmpty()) {
            throw new IllegalArgumentException("숙련도는 필수 입력 항목이며 공백일 수 없습니다.");
        }

        if (request.getYearsOfExp() == null || request.getYearsOfExp() < 0) {
            throw new IllegalArgumentException("경험 연수는 0 이상이어야 하며 필수 입력 항목입니다.");
        }
    }
}
