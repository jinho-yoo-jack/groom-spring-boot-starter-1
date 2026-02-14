package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프로필 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    /** 의존성 주입: Service */
    private final ProfileService profileService;

    /**
     * 생성자 주입
     */
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // ==================== CREATE ====================

    /**
     * 프로필 생성
     * POST /api/v1/profiles
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
        @RequestBody
        ProfileCreateRequest request
    ) {
        // Service 호출하여 프로필 생성
        ProfileResponse response = profileService.createProfile(request);

        // 201 CREATED 상태코드와 함께 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    // ==================== CREATE ====================

    /**
     * 모든 프로필 조회
     * GET /api/v1/profiles
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getAllProfiles() {
        // Service 호출하여 모든 프로필 조회
        List<ProfileResponse> responses = profileService.getAllProfiles();

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(responses));
    }

    /**
     * ID로 프로필 단건 조회
     * GET /api/v1/profiles/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileById(
            @PathVariable
            Long id
    ) {
        // Service 호출하여 ID로 프로필 조회
        ProfileResponse response = profileService.getProfileById(id);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response));
    }

    /**
     * 직무별 프로필 조회
     * GET /api/v1/profiles/position/{position}
     * 예시: GET /api/v1/profiles/position/BACKEND
     *       GET /api/v1/profiles/position/FRONTEND
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getProfileByPosition(
            @PathVariable
            String position
    ) {
        // Service 호출하여 직무로 프로필 조회
        List<ProfileResponse> responses = profileService.getProfileByPosition(position);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(responses));
    }
}
