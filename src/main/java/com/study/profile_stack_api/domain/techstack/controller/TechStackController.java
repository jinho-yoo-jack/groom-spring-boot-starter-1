package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기술 스택 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/profiles/{profileId}/tech-stacks")
public class TechStackController {
    /** 의존성 주입: Service */
    private final TechStackService techStackService;

    /**
     * 생성자 주입
     */
    public TechStackController(TechStackService techStackService) {
        this.techStackService = techStackService;
    }

    // ==================== CREATE ====================

    /**
     * 기술 스택 생성
     * /api/v1/profiles/{profileId}/tech-stacks
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TechStackResponse>> createTechStack(
            @PathVariable
            Long profileId,
            @RequestBody
            TechStackCreateRequest request
    ) {
        // Service 호출하여 프로필 생성
        TechStackResponse response = techStackService.createTechStack(profileId, request);

        // 201 CREATED 상태코드와 함께 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}
