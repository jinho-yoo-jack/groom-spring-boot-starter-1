package com.study.profile_stack_api.domain.tech_stack.controller;

import com.study.profile_stack_api.domain.tech_stack.Facade.TechStackFacade;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackDeleteResponse;
import com.study.profile_stack_api.domain.tech_stack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.tech_stack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;
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
    private final TechStackFacade techStackFacade;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TechStackResponse>>> getTechStacksWithPaging(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TechStackResponse> responsePage = techStackFacade.getTechStacksWithPaging(profileId, page, size);

        return ResponseEntity.ok()
                .body(ApiResponse.success(responsePage));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TechStackResponse>> createTechStack(
            @RequestBody TechStackCreateRequest request,
            @PathVariable Long profileId) {
        TechStackResponse response = techStackFacade.createTechStack(request, profileId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> getTechStacksById(
            @PathVariable Long profileId,
            @PathVariable Long id ) {
        TechStackResponse response = techStackFacade.getTechStackById(profileId, id);
        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> updateTechStack(
            @PathVariable Long id,
            @PathVariable Long profileId,
            @RequestBody TechStackUpdateRequest request) {
        TechStackResponse response = techStackFacade.updateTechStack(id, profileId, request);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackDeleteResponse>> deleteTechStack(
            @PathVariable Long id,
            @PathVariable Long profileId) {
        TechStackDeleteResponse response = techStackFacade.deleteTechStack(id, profileId);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }
}
