package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileDeleteResponse;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // ================ CREATE ==================

    /**
     * 프로필 생성
     * POST /api/v1/profiles
     */
    @PostMapping
    public ProfileResponse craeteProfile(@RequestBody ProfileRequest request) {
        return profileService.save(request);
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
    public ProfileResponse getProfile(@PathVariable long id) {
        return profileService.getProfile(id);
    }

    /**
     * 직무별 프로필 조회
     * @param position
     * @return
     */
    @GetMapping("/position/{position}")
    public List<ProfileResponse> searchProfileByPosition(@PathVariable String position) {
        return profileService.searchProfileByPosition(position);
    }

    // ================ UPDATE ==================

    /**
     * 프로필 수정
     * @param id
     * @param profileRequest
     * @return
     */
    @PutMapping("/{id}")
    public ProfileResponse updateProfileByPosition(
            @PathVariable long id,
            @RequestBody ProfileRequest profileRequest) {

        return profileService.updateProfile(id, profileRequest);
    }

    // ================ DELETE ==================
    @DeleteMapping("/{id}")
    public ProfileDeleteResponse deleteProfileById(@PathVariable long id) {
        return profileService.deleteProfileById(id);
    }
}
