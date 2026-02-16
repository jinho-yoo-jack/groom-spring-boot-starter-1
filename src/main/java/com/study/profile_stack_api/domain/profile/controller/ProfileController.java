package com.study.profile_stack_api.domain.profile.controller;

import com.study.profile_stack_api.domain.profile.dto.ProfileRequest;
import com.study.profile_stack_api.domain.profile.dto.ProfileResponse;
import com.study.profile_stack_api.domain.profile.service.ProfileService;
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
    public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest request) {
        ProfileResponse res = profileService.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long id) {
        ProfileResponse res = profileService.getProfile(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/position/{position}")
    public List<ProfileResponse> findByPosition(@PathVariable String position) {
        List<ProfileResponse> res = profileService.findByPosition(position);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res)
                .getBody();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable Long id, @RequestBody ProfileRequest request) {
        ProfileResponse res = profileService.updateProfile(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("삭제되었습니다.");
    }
}
