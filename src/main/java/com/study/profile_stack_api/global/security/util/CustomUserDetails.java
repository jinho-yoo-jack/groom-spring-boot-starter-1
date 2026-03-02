package com.study.profile_stack_api.global.security.util;

import com.study.profile_stack_api.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired()     { return true; }
    @Override
    public boolean isAccountNonLocked()      { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled()               { return true; }
}