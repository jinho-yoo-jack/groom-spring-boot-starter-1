package com.study.profile_stack_api.domain.techstack.controller;

import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/tech-stacks")
public class TechStackController {
    // === TechStack API ===
    // GET
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getTechStack(@PathVariable Long profileId, @PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "getTechStack | profileId: %s, id: %s".formatted(profileId, id)
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getAllTechStacks(@PathVariable Long profileId) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "getTechStack | profileId: %s,".formatted(profileId)
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addTechStack(@PathVariable Long profileId) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "addTechStack | profileId: %s".formatted(profileId)
        ));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateTechStack(@PathVariable Long profileId, @PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "updateTechStack | profileId: %s, id: %s".formatted(profileId, id)
        ));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTechStack(@PathVariable Long profileId, @PathVariable String id) {
        return ResponseEntity.ok().body(ApiResponse.success(
                "deleteTechStack | profileId: %s, id: %s".formatted(profileId, id)
        ));
    }
}
