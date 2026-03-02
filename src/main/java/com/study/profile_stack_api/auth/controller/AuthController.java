package com.study.profile_stack_api.auth.controller;

import com.study.profile_stack_api.auth.dto.request.LoginRequest;
import com.study.profile_stack_api.auth.dto.request.TokenRefreshRequest;
import com.study.profile_stack_api.auth.dto.request.SignupRequest;
import com.study.profile_stack_api.auth.dto.response.LoginResponse;
import com.study.profile_stack_api.auth.dto.response.SignupResponse;
import com.study.profile_stack_api.auth.dto.response.TokenRefreshResponse;
import com.study.profile_stack_api.auth.service.AuthService;
import com.study.profile_stack_api.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * User signup/registration
     * POST /api/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        log.info("Signup request for username: {}", request.getUsername());

        SignupResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    /**
     * User login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request for username: {}", request.getUsername());

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * User logout (optional - invalidates refresh token)
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@Valid @RequestBody TokenRefreshRequest request) {
        log.info("Logout request");

        authService.logout(request.getRefreshToken());

        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    /**
     * Refresh access token
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        log.info("Token refresh request");

        TokenRefreshResponse response = authService.refresh(request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
