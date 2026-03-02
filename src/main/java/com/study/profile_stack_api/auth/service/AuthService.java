package com.study.profile_stack_api.auth.service;

import com.study.profile_stack_api.auth.dao.MemberDao;
import com.study.profile_stack_api.auth.dao.RefreshTokenDao;
import com.study.profile_stack_api.auth.dto.request.LoginRequest;
import com.study.profile_stack_api.auth.dto.request.TokenRefreshRequest;
import com.study.profile_stack_api.auth.dto.request.SignupRequest;
import com.study.profile_stack_api.auth.dto.response.LoginResponse;
import com.study.profile_stack_api.auth.dto.response.SignupResponse;
import com.study.profile_stack_api.auth.dto.response.TokenRefreshResponse;
import com.study.profile_stack_api.auth.entity.Member;
import com.study.profile_stack_api.auth.entity.MemberRole;
import com.study.profile_stack_api.auth.exception.AuthException;
import com.study.profile_stack_api.auth.exception.InvalidTokenException;
import com.study.profile_stack_api.global.exception.DuplicateUserNameException;
import com.study.profile_stack_api.global.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenDao refreshTokenDao;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        log.info("Signup attempt for username: {}", request.getUsername());

        if (memberDao.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUserNameException(request.getUsername());
        }

        Member newMember = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(MemberRole.USER)
                .build();

        Member savedMember = memberDao.save(newMember);

        log.info("User registered successfully: {}", savedMember.getUsername());

        return SignupResponse.builder()
                .id(savedMember.getId())
                .username(savedMember.getUsername())
                .build();
    }

    public LoginResponse login(@Valid LoginRequest request) {
        try {
            log.info("Login attempt for user: {}", request.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // 2. 인증 성공 → DB에서 사용자 정보 조회
            Member user = memberDao.findByUsername(request.getUsername())
                            .orElseThrow(() -> new AuthException("User not found"));

            // 3. 권한 정보 추출
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            // 4. Access Token + Refresh Token 생성
            String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getUsername(), roles);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

            // 5. Refresh Token을 DB에 저장
            Date refreshTokenExpiry = jwtTokenProvider.getExpirationFromToken(refreshToken);
            refreshTokenDao.saveRefreshToken(user.getId(), refreshToken, new Timestamp(refreshTokenExpiry.getTime()));

            log.info("Login successful for user: {}", user.getUsername());

            return LoginResponse.of(
                    accessToken,
                    refreshToken,
                    accessTokenValidity,
                    user.getUsername()
            );

        } catch (AuthenticationException e) {
            log.error("Login failed for user: {}", request.getUsername(), e);
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    /**
     * Logout (invalidate refresh token)
     */
    @Transactional
    public void logout(String refreshToken) {
        log.info("Logout attempt");
        refreshTokenDao.deleteRefreshToken(refreshToken);
        log.info("Logout successful");
    }

    /**
     * Refresh access token using refresh token
     */
    @Transactional
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        log.info("Token refresh attempt");

        // Validate refresh token
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        // Check if it's a refresh token
        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new InvalidTokenException("Token is not a refresh token");
        }

        // Find user by refresh token
        Member member = memberDao.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found or expired"));

        // Get user roles
        MemberRole userRole = member.getRole() != null ? member.getRole() : MemberRole.USER;
        String roles = "ROLE_" + userRole.name();

        // Generate new tokens
        String newAccessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getUsername(), roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(member.getUsername());

        // Update refresh token in database
        Date refreshTokenExpiry = jwtTokenProvider.getExpirationFromToken(newRefreshToken);
        refreshTokenDao.saveRefreshToken(member.getId(), newRefreshToken, new Timestamp(refreshTokenExpiry.getTime()));

        log.info("Token refreshed successfully for user: {}", member.getUsername());

        return TokenRefreshResponse.of(
                newAccessToken
        );
    }
}
