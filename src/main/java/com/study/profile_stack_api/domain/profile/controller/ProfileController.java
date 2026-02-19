package com.study.profile_stack_api.domain.profile.controller;

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

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.Page;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> getProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProfileResponse> result = profileService.getProfiles(page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/position")
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> getProfilesByPosition(
            @RequestParam String position,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProfileResponse> result = profileService.getProfilesByPosition(position, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProfileResponse>>> searchProfiles(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProfileResponse> result = profileService.searchProfilesByName(name, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable(name = "id") Long id) {
        ProfileResponse profile = profileService.getProfile(id);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(
            @RequestBody ProfileCreateRequest request) {
        ProfileResponse profile = profileService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @PathVariable(name = "id") Long id,
            @RequestBody ProfileUpdateRequest request) {
        ProfileResponse profile = profileService.updateProfile(id, request);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable(name = "id") Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}