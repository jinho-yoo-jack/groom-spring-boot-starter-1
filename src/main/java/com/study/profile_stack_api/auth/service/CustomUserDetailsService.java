package com.study.profile_stack_api.auth.service;

import com.study.profile_stack_api.auth.dao.MemberDao;
import com.study.profile_stack_api.auth.entity.Member;
import com.study.profile_stack_api.auth.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetailsService implementation
 * Loads user-specific data from the database
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);

        Member member = memberDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username : " + username));

        log.debug("User found: {}", member.getUsername());

        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(getAuthorities(member.getRole()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }

    /**
     * Convert user role to GrantedAuthority collection
     */
    private Collection<? extends GrantedAuthority> getAuthorities(MemberRole role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Convert UserRole enum to Spring Security authority
        if (role != null) {
            String roleStr = "ROLE_" + role.name();
            authorities.add(new SimpleGrantedAuthority(roleStr));
        } else {
            // Default role
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }
}
