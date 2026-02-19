package com.study.profile_stack_api.domain.techstack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.entity.Category;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import com.study.profile_stack_api.domain.techstack.repository.TechStackRepository;
import com.study.profile_stack_api.global.common.Page;

@Service
public class TechStackService {
    private final TechStackRepository techStackRepository;
    private final ProfileRepository profileRepository;

    public TechStackService(TechStackRepository techStackRepository, ProfileRepository profileRepository) {
        this.techStackRepository = techStackRepository;
        this.profileRepository = profileRepository;
    }

    public TechStackResponse createTechStack(TechStackCreateRequest request) {
        profileRepository.findById(request.getProfileId());

        TechStack techStack = TechStack.builder()
                .profileId(request.getProfileId())
                .name(request.getName())
                .category(Category.valueOf(request.getCategory()))
                .proficiency(Proficiency.valueOf(request.getProficiency()))
                .yearsOfExp(request.getYearsOfExp())
                .build();

        TechStack saved = techStackRepository.save(techStack);
        return TechStackResponse.from(saved);
    }

    public TechStackResponse getTechStackById(Long id) {
        TechStack techStack = techStackRepository.findById(id);
        return TechStackResponse.from(techStack);
    }

    public List<TechStackResponse> getTechStacksByProfileId(Long profileId) {
        List<TechStack> techStacks = techStackRepository.findByProfileId(profileId);
        return techStacks.stream().map(TechStackResponse::from).toList();
    }

    public Page<TechStackResponse> getTechStacksByProfileId(Long profileId, int page, int size) {
        List<TechStack> techStacks = techStackRepository.findByProfileId(profileId, page, size);
        long totalElements = techStackRepository.countByProfileId(profileId);
        List<TechStackResponse> content = techStacks.stream().map(TechStackResponse::from).toList();
        return Page.of(content, page, size, totalElements);
    }

    public Page<TechStackResponse> getTechStacksByCategory(Long profileId, String category, int page, int size) {
        List<TechStack> techStacks = techStackRepository.findByProfileIdAndCategory(profileId, category, page, size);
        long totalElements = techStackRepository.countByProfileIdAndCategory(profileId, category);
        List<TechStackResponse> content = techStacks.stream().map(TechStackResponse::from).toList();
        return Page.of(content, page, size, totalElements);
    }

    public Page<TechStackResponse> getTechStacksByProficiency(Long profileId, String proficiency, int page, int size) {
        List<TechStack> techStacks = techStackRepository.findByProfileIdAndProficiency(profileId, proficiency, page,
                size);
        long totalElements = techStackRepository.countByProfileIdAndProficiency(profileId, proficiency);
        List<TechStackResponse> content = techStacks.stream().map(TechStackResponse::from).toList();
        return Page.of(content, page, size, totalElements);
    }

    public TechStackResponse updateTechStack(Long id, TechStackUpdateRequest request) {
        TechStack existing = techStackRepository.findById(id);

        TechStack techStack = TechStack.builder()
                .id(id)
                .profileId(existing.getProfileId())
                .name(request.getName())
                .category(Category.valueOf(request.getCategory()))
                .proficiency(Proficiency.valueOf(request.getProficiency()))
                .yearsOfExp(request.getYearsOfExp())
                .build();

        TechStack updated = techStackRepository.update(techStack);
        return TechStackResponse.from(updated);
    }

    public void deleteTechStackById(Long id) {
        techStackRepository.findById(id);
        techStackRepository.deleteById(id);
    }
}