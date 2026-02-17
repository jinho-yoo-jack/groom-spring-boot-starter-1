package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class TechStackController {

    private final TechStackService techStackService;

    // =============== CREATE ==================

    /**
     * 기슬 스택 생성
     *
     * @param profileId
     * @return
     */
    @PostMapping("/{profileId}/tech-stacks")
    public TechStackResponse createTechstack(@PathVariable long profileId, @RequestBody TechStackRequest techstackRequest) {
        return techStackService.save(profileId, techstackRequest);
    }

    // =============== READ ==================

    /**
     * 기술 스택 목록 조회 (Read - List, 페이징)
     *
     * @param profileId
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/{profileId}/tech-stacks")
    public Page<TechStackResponse> getAllTechStacks(
            @PathVariable long profileId,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "10") int page,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String proficiency) {

        return techStackService.getAllTechStack(profileId, size, page, category, proficiency);
    }

    /**
     * 기술 스택 단건 조회
     *
     * @param profileId
     * @param id
     * @return
     */
    @GetMapping("/{profileId}/tech-stacks/{id}")
    public TechStackResponse getTechStack(
            @PathVariable long profileId,
            @PathVariable long id) {

        return techStackService.getTechStack(profileId, id);
    }

    // =============== UPDATE ==================

    /**
     * 기술 스택 수정
     * @param profileId
     * @param id
     * @param techStackRequest
     * @return
     */
    @PutMapping("/{profileId}/tech-stacks/{id}")
    public TechStackResponse updateTechStack(
            @PathVariable long profileId,
            @PathVariable long id,
            @RequestBody TechStackRequest techStackRequest) {

        return techStackService.updateTechStack(profileId, id, techStackRequest);
    }

    // =============== UPDATE ==================

    /**
     * 기술 스택 삭제
     * @param profileId
     * @param id
     * @return
     */
    @DeleteMapping("/{profileId}/tech-stacks/{id}")
    public TechStackDeleteResponse deleteTechStack(
            @PathVariable long profileId,
            @PathVariable long id) {

        return techStackService.deleteTechStack(profileId, id);
    }
}
