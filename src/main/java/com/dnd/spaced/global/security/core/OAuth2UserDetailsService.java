package com.dnd.spaced.global.security.core;

import com.dnd.spaced.domain.auth.domain.TokenDecoder;
import com.dnd.spaced.domain.auth.domain.TokenType;
import com.dnd.spaced.domain.auth.infrastructure.PrivateClaims;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class OAuth2UserDetailsService implements UserDetailsService {

    private final TokenDecoder tokenDecoder;

    @Override
    public OAuth2UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        return tokenDecoder.decode(TokenType.ACCESS, token)
                .map(this::convert)
                .orElse(null);
    }

    private OAuth2UserDetails convert(PrivateClaims privateClaims) {
        return new OAuth2UserDetails(
                privateClaims.email(),
                Set.of(new SimpleGrantedAuthority(privateClaims.roleName()))
        );
    }
}
