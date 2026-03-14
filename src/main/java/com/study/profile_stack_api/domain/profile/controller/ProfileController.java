package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileSearchCondition;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteAllResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.domain.profile.validation.UniqueEmailOnUpdate;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프로필 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/profiles")
@Validated
@RequiredArgsConstructor
public class ProfileController {
    /** 의존성 주입: Service */
    private final ProfileService profileService;

    // ==================== CREATE ====================

    /**
     * 프로필 생성
     * POST /api/v1/profiles
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
        @Valid
        @RequestBody
        ProfileCreateRequest request,
        @AuthenticationPrincipal
        UserDetails userDetails
    ) {
        // Service 호출하여 프로필 생성
        ProfileResponse response = profileService.createProfile(request, userDetails.getUsername());

        // 201 CREATED 상태코드와 함께 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    // ==================== READ ====================

    /**
     * 모든 프로필 조회
     * GET /api/v1/profiles
     * GET /api/v1/profiles?name={name}
     * GET /api/v1/profiles?position={position}
     */
    @GetMapping(params = {"!page", "!size"})
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getAllProfiles(
            @RequestParam(required = false)
            String name,
            @RequestParam(required = false)
            String position
    ) {
        // 검색 상태 확인
        ProfileSearchCondition condition = new ProfileSearchCondition(name, position, 0, 0);

        // Service 호출하여 상태에 따른 전체 프로필 조회
        List<ProfileResponse> responses = profileService.getSearchProfiles(condition);

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
    @GetMapping(path = "/position/{position}", params = {"!page", "!size"})
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

    // ==================== PAGING ====================

    /**
     * 전체 프로필 페이징 조회
     *
     * @param page 페이지 번호 (0-based, 기본값: 0)
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @return 페이징된 프로필
     */
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> getProfilesWithPaging(
            @RequestParam(required = false)
            String name,
            @RequestParam(required = false)
            String position,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size
    ) {
        // 검색 상태 확인
        ProfileSearchCondition condition = new ProfileSearchCondition(name, position, page, size);

        // Service 호출하여 상태에 따른 전체 프로필 페이징 조회
        Page<ProfileResponse> response = profileService.getSearchProfilesWithPaging(condition);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response));
    }

    /**
     * 직무별 프로필 페이징 조회
     *
     * @param position 직무
     * @param page 페이지 번호 (0-based, 기본값: 0)
     * @param size 페이지 크기 (기본값: 10, 최대값: 100)
     * @return 페이징된 프로필
     */
    @GetMapping(path = "/position/{position}", params = {"page", "size"})
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> getProfilesByPositionWithPaging(
            @PathVariable
            String position,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size
    ) {
        // Service 호출하여 직무별 프로필 페이징 조회
        Page<ProfileResponse> response = profileService.getProfilesByPositionWithPaging(position, page, size);

        // 200 OK 상태 코드와 함께 응답
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response));
    }

    // ==================== UPDATE ====================

    /**
     * 프로필 수정
     * PUT /api/v1/profiles/{id}
     */
    @PutMapping("/{id}")
    @UniqueEmailOnUpdate
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
        @PathVariable
        Long id,
        @Valid
        @RequestBody
        ProfileUpdateRequest request,
        @AuthenticationPrincipal
        UserDetails userDetails
    ) {
        ProfileResponse response = profileService.updateProfile(id, request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ==================== DELETE ====================

    /**
     * 프로필 한개를 삭제
     * DELETE /api/v1/profiles/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDeleteResponse>> deleteProfile(
            @PathVariable
            Long id,
            @AuthenticationPrincipal
            UserDetails userDetails
    ) {
        ProfileDeleteResponse response = profileService.deleteProfile(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 전체 프로필 삭제
     * DELETE /api/v1/profiles
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<ProfileDeleteAllResponse>> deleteAllProfiles(
            @AuthenticationPrincipal
            UserDetails userDetails
    ) {
        ProfileDeleteAllResponse response = profileService.deleteAllProfiles(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
