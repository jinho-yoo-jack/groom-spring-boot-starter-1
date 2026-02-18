package com.study.profile_stack_api.domain.techstack.controller;


import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/tech-stacks")
public class TechStackController {
    private final TechStackService techStackService;
    public TechStackController(TechStackService techStackService) {
        this.techStackService = techStackService;
    }

    //기술 스택 추가
    @PostMapping
    public TechStackResponse createTechStack(
            @PathVariable Long profileId,
            @RequestBody TechStackCreateRequest request){
        TechStackResponse response = techStackService.create(profileId, request);
        return response;
    }

    //기술 스택 목록 조회 (페이징)
    @GetMapping
    public Page<TechStackResponse> getTechStacks(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return techStackService.getList(profileId, page, size);
    }

    //기술 스택 단건 조회
    @GetMapping("/{id}")
    public TechStackResponse getTechStackById(@PathVariable Long profileId, @PathVariable Long id) {
        TechStackResponse response = techStackService.getById(profileId, id);
        return response;
    }

    //기술 스택 수정
    @PutMapping("/{id}")
    public TechStackResponse updateTechStack(
            @PathVariable Long profileId,
            @PathVariable Long id,
            @RequestBody TechStackUpdateRequest request){
        return techStackService.updateTechStack(profileId, id, request);
    }

    //기술 스택 삭제
    @DeleteMapping("/{id}")
    public String deleteTechStack(@PathVariable Long profileId, @PathVariable Long id) {
        techStackService.deleteTechStack(profileId, id);
        return profileId + "번 프로필의 " + id + "번 기술 스택 삭제";
    }

}
