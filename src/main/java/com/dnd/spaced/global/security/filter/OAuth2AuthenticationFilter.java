package com.dnd.spaced.global.security.filter;

import com.dnd.spaced.global.security.authentication.OAuth2AuthenticationToken;
import com.dnd.spaced.global.security.core.OAuth2UserDetails;
import com.dnd.spaced.global.security.core.OAuth2UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class OAuth2AuthenticationFilter extends OncePerRequestFilter {

    private final OAuth2UserDetailsService oAuth2UserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        extractToken(request).map(oAuth2UserDetailsService::loadUserByUsername)
                             .ifPresent(this::setAuthentication);

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(OAuth2UserDetails oAuth2UserDetails) {
        SecurityContextHolder.getContext()
                             .setAuthentication(
                                     new OAuth2AuthenticationToken(
                                             oAuth2UserDetails,
                                             oAuth2UserDetails.getAuthorities()
                                     )
                             );
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
