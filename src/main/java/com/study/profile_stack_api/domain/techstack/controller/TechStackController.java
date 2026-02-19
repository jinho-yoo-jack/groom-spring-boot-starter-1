package com.study.profile_stack_api.domain.techstack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.profile_stack_api.domain.techstack.dto.request.TechStackCreateRequest;
import com.study.profile_stack_api.domain.techstack.dto.request.TechStackUpdateRequest;
import com.study.profile_stack_api.domain.techstack.dto.response.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;

@RestController
@RequestMapping("/api/v1/tech-stacks")
public class TechStackController {

    private final TechStackService techStackService;

    public TechStackController(TechStackService techStackService) {
        this.techStackService = techStackService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TechStackResponse>>> getTechStacksByProfileId(
            @RequestParam Long profileId) {
        List<TechStackResponse> result = techStackService.getTechStacksByProfileId(profileId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiResponse<Page<TechStackResponse>>> getTechStacksByProfileIdPaging(
            @RequestParam Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TechStackResponse> result = techStackService.getTechStacksByProfileId(profileId, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<Page<TechStackResponse>>> getTechStacksByCategory(
            @RequestParam Long profileId,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TechStackResponse> result = techStackService.getTechStacksByCategory(profileId, category, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/proficiency")
    public ResponseEntity<ApiResponse<Page<TechStackResponse>>> getTechStacksByProficiency(
            @RequestParam Long profileId,
            @RequestParam String proficiency,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TechStackResponse> result = techStackService.getTechStacksByProficiency(profileId, proficiency, page,
                size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TechStackResponse>> createTechStack(
            @RequestBody TechStackCreateRequest request) {
        TechStackResponse response = techStackService.createTechStack(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> updateTechStack(
            @PathVariable(name = "id") Long id,
            @RequestBody TechStackUpdateRequest request) {
        TechStackResponse response = techStackService.updateTechStack(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechStack(
            @PathVariable(name = "id") Long id) {
        techStackService.deleteTechStackById(id);
        return ResponseEntity.noContent().build();
    }
}
