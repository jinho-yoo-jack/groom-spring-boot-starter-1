package com.study.profile_stack_api.domain.auth.service;

import com.study.profile_stack_api.domain.auth.dao.MemberDao;
import com.study.profile_stack_api.domain.auth.dao.RefreshTokenDao;
import com.study.profile_stack_api.domain.auth.dto.request.LoginRequest;
import com.study.profile_stack_api.domain.auth.dto.request.SignupRequest;
import com.study.profile_stack_api.domain.auth.dto.response.LoginResponse;
import com.study.profile_stack_api.domain.auth.dto.response.SignupResponse;
import com.study.profile_stack_api.domain.auth.entity.Member;
import com.study.profile_stack_api.domain.auth.entity.RefreshToken;
import com.study.profile_stack_api.domain.auth.entity.Role;
import com.study.profile_stack_api.domain.auth.exception.DuplicateResourceException;
import com.study.profile_stack_api.domain.auth.mapper.AuthMapper;
import com.study.profile_stack_api.global.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private  AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RefreshTokenDao refreshTokenDao;

    @Mock
    private MemberDao memberDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthMapper authMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // Given
        // memberDao.existsByUsername()이 false를 반환하도록 설정
        // passwordEncoder.encode()가 암호화된 문자열을 반환하도록 설정
        // memberDao.save()가 ID가 부여된 Member를 반환하도록 설정
        SignupRequest request = new SignupRequest("testuser", "password123");
        String encodedPassword = "encoded-password123";

        Member newMember = Member.builder()
                .username("testuser")
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        Member savedMember = Member.builder()
                .id(1L)
                .username("testuser")
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        SignupResponse signupResponse = SignupResponse.of(1L, "testuser");

        when(memberDao.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
        when(authMapper.toEntity(request, encodedPassword)).thenReturn(newMember);
        when(memberDao.save(newMember)).thenReturn(savedMember);
        when(authMapper.toResponse(savedMember)).thenReturn(signupResponse);

        // When
        SignupResponse response = authService.signup(request);

        // Then
        // response의 username이 올바른지 검증하세요
        // memberDao.save()가 한 번 호출되었는지 검증하세요
        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getMessage()).isEqualTo("회원가입이 성공적으로 완료되었습니다.");

        verify(memberDao).existsByUsername("testuser");
        verify(passwordEncoder).encode("password123");
        verify(authMapper).toEntity(request, encodedPassword);
        verify(memberDao, times(1)).save(newMember);
        verify(authMapper).toResponse(savedMember);
    }

    @Test
    @DisplayName("중복된 username으로 회원가입 시 예외 발생")
    void signup_duplicateUsername() {
        // Given
        // memberDao.existsByUsername()이 true를 반환하도록 설정
        SignupRequest request = new SignupRequest("testuser", "password123");
        when(memberDao.existsByUsername("testuser")).thenReturn(true);

        // When & Then
        // signup 호출 시 DuplicateResourceException 발생하는지 검증하세요
        // memberDao.save()가 호출되지 않았는지 검증
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("이미 사용 중인 아이디입니다. (username: testuser)");

        verify(passwordEncoder, never()).encode(anyString());
        verify(authMapper, never()).toEntity(any(), anyString());
        verify(memberDao, never()).save(any());
    }

    @Test
    @DisplayName("로그인 성공 시 JWT 토큰 반환")
    void login_success() {
        // Given
        // memberDao.findByUsername()이 member를 반환하도록 설정
        // passwordEncoder.matches()가 true를 반환하도록 설정
        // jwtTokenProvider의 메서드들이 토큰을 반환하도록 설정
        ReflectionTestUtils.setField(authService, "accessTokenExpiration", 1_800_000L);

        LoginRequest request = new LoginRequest("testuser", "password123");

        Member member = Member.builder()
                .id(1L)
                .username("testuser")
                .password("encoded-password123")
                .role(Role.USER)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "testuser",
                "password123",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String accessToken = "access-token";
        String refreshTokenValue = "refresh-token";

        RefreshToken refreshToken = RefreshToken.builder()
                .id(1L)
                .token("refresh-token")
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(memberDao.findByUsername("testuser")).thenReturn(Optional.of(member));
        when(jwtTokenProvider.createAccessToken("testuser", "ROLE_USER")).thenReturn(accessToken);
        when(jwtTokenProvider.createRefreshToken(1L, "testuser")).thenReturn(refreshToken);

        // When
        LoginResponse response = authService.login(request);

        // Then
        // response에 accessToken이 있는지 검증
        // tokenType이 "Bearer"인지 검증
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshTokenValue);
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getExpiresIn()).isEqualTo(1_800_000L);
        assertThat(response.getUsername()).isEqualTo("testuser");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(memberDao).findByUsername("testuser");
        verify(jwtTokenProvider).createAccessToken("testuser", "ROLE_USER");
        verify(jwtTokenProvider).createRefreshToken(1L, "testuser");
        verify(refreshTokenDao).saveRefreshToken(refreshToken);
    }
}
