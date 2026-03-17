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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "프로필", description = "프로필 CRUD API - JWT 인증 필요")
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
    @Operation(
            summary = "프로필 생성",
            description = """
                    새로운 프로필을 생성합니다.
                    
                    ### 검증 규칙
                    - **name**: 필수, 50자 이하
                    - **email**: 필수, 이메일 형식, 100자 이하, 고유해야 함
                    - **bio**: 선택, 500자 이하
                    - **position**: 필수, BACKEND/FRONTEND/DEVOPS/FULLSTACK/MOBILE/DATA/AI/ETC 중 선택
                    - **careerYears**: 필수, 0 이상
                    - **githubUrl**: 선택, 200자 이하
                    - **blogUrl**: 선택, 200자 이하
                    
                    ### 주의사항
                    - 이메일은 고유해야 하며, 이미 존재하는 이메일로 생성 시 400 Bad Request 반환
                    - JWT 토큰 인증이 필요합니다
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 - 입력값 검증 실패",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "success": false,
                                              "data": null,
                                              "error": {
                                                "code": "VALIDATION_ERROR",
                                                "message": "name: 이름은 필수입니다."
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 - JWT 토큰이 없거나 유효하지 않음"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "프로필 생성 요청 데이터",
                required = true,
                content = @Content(
                        schema = @Schema(implementation = ProfileCreateRequest.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "name": "API Tester",
                                          "email": "api-profile-1@example.com",
                                          "bio": "HTTP client profile test",
                                          "position": "BACKEND",
                                          "careerYears": 2,
                                          "githubUrl": "https://github.com/api-profile",
                                          "blogUrl": "https://api-profile.dev"
                                        }
                                        """
                        )
                )
        )
        @Valid
        @RequestBody
        ProfileCreateRequest request,
        @AuthenticationPrincipal
        UserDetails userDetails
    ) {
        log.info("POST /api/v1/profiles - Create profile name: {}", request.getName());

        // Service 호출하여 프로필 생성
        ProfileResponse response = profileService.createProfile(request, userDetails.getUsername());

        log.info("Profile created successfully with ID: {}", response.getId());

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
        ProfileSearchCondition condition = ProfileSearchCondition.of(name, position, 0, 0);

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
        ProfileSearchCondition condition = ProfileSearchCondition.of(name, position, page, size);

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
