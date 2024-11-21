package com.dnd.spaced.global.security.authentication;

import com.dnd.spaced.global.security.core.OAuth2UserDetails;
import com.dnd.spaced.global.security.exception.UnsupportedSecurityOperationException;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OAuth2AuthenticationToken extends AbstractAuthenticationToken {

    private OAuth2UserDetails principal;

    public OAuth2AuthenticationToken(OAuth2UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedSecurityOperationException();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
