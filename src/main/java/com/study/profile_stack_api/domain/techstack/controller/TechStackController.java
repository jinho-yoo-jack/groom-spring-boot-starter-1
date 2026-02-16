package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class TechStackController {

    private final TechStackService techstackService;

    // =============== CREATE ==================

    /**
     * 기슬 스택 생성
     * @param profileId
     * @return
     */
    @PostMapping("/{profileId}/tech-stacks")
    public TechStackResponse createTechstack(@PathVariable long profileId, @RequestBody TechStackRequest techstackRequest) {
        return techstackService.save(profileId, techstackRequest);
    }
}
