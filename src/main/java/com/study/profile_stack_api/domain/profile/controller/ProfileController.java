package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
import com.study.profile_stack_api.global.common.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {


    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //프로필 생성
    @PostMapping
    public ProfileResponse createProfile(
            @RequestBody ProfileCreateRequest request){

        return profileService.createProfile(request);
    }

    //프로필 목록 조회
    @GetMapping
    public List<ProfileResponse> getAllProfiles(){
        return profileService.getAllProfiles();
    }

    @GetMapping("/paging")
    public Page<ProfileResponse> getProfileWithPaging(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size){
        return profileService.getProfileWithPaging(page, size);
    }


    //프로필 단건 조회
    @GetMapping("/{id}")
    public ProfileResponse getProfile(@PathVariable Long id){
        return profileService.getProfileById(id);
    }

    //직무별 프로필 조회
    @GetMapping("/position/{position}")
    public List<ProfileResponse> getProfilesByPosition(@PathVariable String position) {
        return profileService.getProfileByPosition(position);
    }

    //프로필 수정
    @PutMapping("/{id}")
    public String updateProfile(@PathVariable Long id,
                                @RequestBody ProfileUpdateRequest request){
        ProfileResponse response = profileService.updateProfile(id, request);
        return id + "번 프로필 수정";
    }

    //프로필 삭제
    @DeleteMapping("/{id}")
    public String deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return id + "번 프로필 삭제";
    }
}
