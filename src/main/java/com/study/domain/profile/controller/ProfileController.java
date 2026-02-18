package com.study.domain.profile.controller;

import com.study.domain.profile.dto.request.ProfileCreateRequest;
import com.study.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.domain.profile.dto.response.ProfileResponse;
import com.study.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.domain.profile.entity.Profile;
import com.study.domain.profile.service.ProfileService;

import com.study.global.common.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    /**
     * 프로필 생성
     * POST /api/v1/profiles
     * 성공: 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(@RequestBody ProfileCreateRequest request) {
        return service.createProfile(request);
    }

    /**
     * 프로필 목록 조회 (페이징 + 필터)
     * GET /api/v1/profiles?page=0&size=10&position=BACKEND&name=김
     * 성공: 200 OK
     */
    @GetMapping
    public Page<ProfileResponse> getProfiles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String name
    ) {
        return service.getProfilesWithPaging(page, size, position, name);
    }

    /**
     * 프로필 단건 조회
     * GET /api/v1/profiles/{id}
     * 성공: 200 OK
     */
    @GetMapping("/{id}")
    public ProfileResponse getProfile(@PathVariable Long id) {
        return service.getProfileById(id);
    }

    /**
     * 직무별 프로필 조회 (명세: 배열 반환)
     * GET /api/v1/profiles/position/{position}
     * 성공: 200 OK
     */
    @GetMapping("/position/{position}")
    public List<ProfileResponse> getProfilesByPosition(@PathVariable String position) {
        return service.getProfilesByPosition(position);
    }

    /**
     * 프로필 수정
     * PUT /api/v1/profiles/{id}
     * 성공: 200 OK
     */
    @PutMapping("/{id}")
    public ProfileResponse updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        return service.updateProfile(id, request);
    }

    /**
     * 프로필 삭제 (명세: 200 OK + {message, deletedId})
     * DELETE /api/v1/profiles/{id}
     * 성공: 200 OK
     */
    @DeleteMapping("/{id}")
    public ProfileDeleteResponse deleteProfile(@PathVariable Long id) {
        return service.deleteProfile(id);
    }
}