package com.study.profile_stack_api.domain.tech_stack.controller;

import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{profileId}/tech-stacks")
@RequiredArgsConstructor
@Validated
public class TechStackController {
    private final TechStackService techStackService;

    @PostMapping
    public ResponseEntity<ApiResponse<TechStackResponse>> createTechStack(
            @RequestBody TechStackCreateRequest request,
            @PathVariable Long profileId) {
        TechStackResponse response = techStackService.createTechStack(request, profileId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TechStackResponse>>> getAllTechStacks(
            @PathVariable Long profileId) {
        List<TechStackResponse> responses = techStackService.getAllTechStacks(profileId);

        return ResponseEntity.ok()
                .body(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> getTechStacksById(
            @PathVariable Long profileId,
            @PathVariable Long id ) {
        TechStackResponse response = techStackService.getTechStackById(profileId, id);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }
}
