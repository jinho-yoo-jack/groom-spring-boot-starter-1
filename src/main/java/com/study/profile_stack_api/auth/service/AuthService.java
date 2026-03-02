package com.study.profile_stack_api.auth.service;

import com.study.profile_stack_api.auth.dao.MemberDao;
import com.study.profile_stack_api.auth.dto.SignupRequest;
import com.study.profile_stack_api.auth.dto.SignupResponse;
import com.study.profile_stack_api.auth.entity.Member;
import com.study.profile_stack_api.auth.entity.MemberRole;
import com.study.profile_stack_api.global.exception.DuplicateUserNameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;


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
}
