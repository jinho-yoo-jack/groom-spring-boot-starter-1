package com.study.profile_stack_api.controller;

import com.study.profile_stack_api.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok().body(ApiResponse.success("Hello World"));
    }
}
