package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 프로필별 기술 스택 생성
     * POST /api/v1/profiles/{profileId}/tech-stacks
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TechStackResponse>> createTechStackByProfileId(
            @PathVariable
            Long profileId,
            @RequestBody
            TechStackCreateRequest request
    ) {
        // Service 호출하여 프로필 생성
        TechStackResponse response = techStackService.createTechStackByProfileId(profileId, request);

        // 201 CREATED 상태코드와 함께 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    // ==================== READ ====================

    /**
     * 프로필별 기술 스택 전체 조회
     * GET /api/v1/profiles/{profileId}/tech-stacks
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TechStackResponse>>> getAllTechStacksByProfileId(
            @PathVariable
            Long profileId
    ) {
        // Service 호출하여 모든 프로필 조회
        List<TechStackResponse> responses = techStackService.getAllTechStacksByProfileId(profileId);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(responses));
    }

    /**
     * 프로필별 기술 스택 ID로 단건 조회
     * GET /api/v1/profiles/{profileId}/tech-stacks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> getTechStackByProfileIdAndId(
            @PathVariable
            Long profileId,
            @PathVariable
            Long id
    ) {
        // Service 호출하여 ID로 프로필 조회
        TechStackResponse response = techStackService.getTechStackByProfileIdAndId(profileId, id);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response));
    }
}
