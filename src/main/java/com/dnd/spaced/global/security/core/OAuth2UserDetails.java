package com.dnd.spaced.global.security.core;

import com.dnd.spaced.global.security.exception.UnsupportedSecurityOperationException;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class OAuth2UserDetails implements UserDetails {

    private final String email;
    private final Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        throw new UnsupportedSecurityOperationException();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedSecurityOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedSecurityOperationException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedSecurityOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedSecurityOperationException();
    }
}
