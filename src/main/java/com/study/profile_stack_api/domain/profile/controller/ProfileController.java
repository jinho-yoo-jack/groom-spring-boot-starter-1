package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
