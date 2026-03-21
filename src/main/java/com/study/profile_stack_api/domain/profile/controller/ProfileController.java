package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestBody ProfileCreateRequest request) {
        ProfileResponse profileResponse = profileService.createProfile(request); // service의 create 구현
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(profileResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileById(
            @PathVariable Long id ) {
        ProfileResponse profileResponse = profileService.getProfileByIdToDto(id);
        return ResponseEntity.ok()
                .body(ApiResponse.success(profileResponse));
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> getProfilesByPosition(
            @PathVariable String position) {
        List<ProfileResponse> profileResponses = profileService.getProfilesByPosition(position);
        return ResponseEntity.ok()
                .body(ApiResponse.success(profileResponses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request) {

        ProfileResponse response = profileService.updateProfile(id, request);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileDeleteResponse>> deleteProfile(
            @PathVariable Long id ) {

        ProfileDeleteResponse response = profileService.deleteProfile(id);

        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> getProfilesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ProfileResponse> responses = profileService.getProfilesWithPaging(page, size);

        return ResponseEntity.ok()
                .body(ApiResponse.success(responses));
    }

}
