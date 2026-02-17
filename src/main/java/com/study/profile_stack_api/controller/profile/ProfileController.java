package com.study.profile_stack_api.controller.profile;

import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    // === Profile API ===
    // GET
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getProfile(@PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success("getProfile | id: %s".formatted(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getAllProfiles() {
        return ResponseEntity.ok().body(ApiResponse.success("getAllProfiles"));
    }

    // POST
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProfile() {
        return ResponseEntity.ok().body(ApiResponse.success("createProfile"));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateProfile(@PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success("updateProfile | id: %s".formatted(id)));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProfile(@PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success("deleteProfile | id: %s".formatted(id)));
    }
}
