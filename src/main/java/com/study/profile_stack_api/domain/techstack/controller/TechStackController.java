package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.domain.techstack.dto.TechStackRequest;
import com.study.profile_stack_api.domain.techstack.dto.TechStackResponse;
import com.study.profile_stack_api.domain.techstack.service.TechStackService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.PageRequest;
import com.study.profile_stack_api.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles/{profileId}/tech-stacks")
public class TechStackController {
    private final TechStackService techStackService;

    @PostMapping()
    public ResponseEntity<ApiResponse<TechStackResponse>> addTechStack(@PathVariable Long profileId, @RequestBody TechStackRequest request) {
        TechStackResponse res = techStackService.addTechStack(profileId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(res, "기술 스택을 성공적으로 추가했습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TechStackResponse>>> findAllWithPaging(PageRequest request, @PathVariable Long profileId) {
        PageResponse<TechStackResponse> res = techStackService.findAllWithPaging(request.getPage(), request.getSize(), profileId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, profileId + "번 프로필의 기술 스택 목록 조회에 성공했습니다."));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> getTechStack(@PathVariable Long profileId, @PathVariable Long id) {
        TechStackResponse res = techStackService.getTechStack(profileId, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, profileId + "번 프로필의 기술 스택 조회에 성공했습니다."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TechStackResponse>> updateTechStack(
            @PathVariable Long profileId, @PathVariable Long id, @RequestBody TechStackRequest request) {
        TechStackResponse res = techStackService.updateTechStack(profileId, id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, "기술 스택이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteTechStack(@PathVariable Long profileId, @PathVariable Long id) {
        techStackService.deleteTechStack(profileId, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.noDataSuccess("기술 스택 삭제에 성공했습니다."));
    }


}
