package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Validated
public class ProfileController {

    private final ProfileService profileService;

    // ================ CREATE ==================

    /**
     * 프로필 생성
     * POST /api/v1/profiles
     */
    @PostMapping
    public  ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
            @Valid  @RequestBody ProfileRequest request,
            ServletResponse servletResponse) {

        return ResponseEntity
                .ok()
                .body(ApiResponse.success(profileService.save(request)));

    }

    // ================ READ ==================

    /**
     * 프로필 모두 조회
     * GET /api/v1/profiles?page=0&size=10
     *
     * @param page
     * @param size
     * @param position
     * @param name
     * @return
     */
    @GetMapping
    public Page<ProfileResponse> getAllProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String name) {

        return profileService.getAllProfiles(page, size, position, name);
    }

    /**
     * 프로필 단건 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @PathVariable
            @Positive(message = "{profile.id.positive}") long id) {

        return ResponseEntity
                .ok()
                .body(ApiResponse.success(profileService.getProfile(id)));
    }

    /**
     * 직무별 프로필 조회
     * @param position
     * @return
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> searchProfileByPosition(@PathVariable String position) {
        //return profileService.searchProfileByPosition(position);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(profileService.searchProfileByPosition(position)));
    }

    // ================ UPDATE ==================

    /**
     * 프로필 수정
     * @param id
     * @param ProfileUpdateRequest
     * @return
     */
    @PutMapping("/{id}")
    public ProfileResponse updateProfileByPosition(
            @PathVariable long id,
            @Valid @RequestBody ProfileUpdateRequest ProfileUpdateRequest) {

        return profileService.updateProfile(id, ProfileUpdateRequest);
    }

    // ================ DELETE ==================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDeleteResponse>> deleteProfileById(@PathVariable long id) {

        return ResponseEntity
                .ok()
                .body(ApiResponse.success(profileService.deleteProfileById(id)));
    }
}
