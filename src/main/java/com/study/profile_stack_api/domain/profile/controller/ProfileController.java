package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.ApiResponse;
import com.study.profile_stack_api.global.common.PageRequest;
import com.study.profile_stack_api.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestBody ProfileRequest request) {
        ProfileResponse res = profileService.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(res, "프로필이 성공적으로 생성되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProfileResponse>>> findAllWithPaging(PageRequest request) {
        PageResponse<ProfileResponse> res = profileService.findAllWithPaging(request.getPage(), request.getSize());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, "프로필 조회에 성공했습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@PathVariable Long id) {
        ProfileResponse res = profileService.getProfile(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, id + "번 프로필 조회에 성공했습니다."));
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<ApiResponse<List<ProfileResponse>>> findByPosition(@PathVariable String position) {
        List<ProfileResponse> res = profileService.findByPosition(position);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, position + " 조회에 성공했습니다."));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@PathVariable Long id, @RequestBody ProfileRequest request) {
        ProfileResponse res = profileService.updateProfile(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(res, "프로필이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.noDataSuccess("프로필 삭제에 성공했습니다."));
    }
}
