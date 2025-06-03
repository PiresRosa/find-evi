package com.dnd.spaced.domain.auth.presentation;

import com.dnd.spaced.domain.auth.application.AuthService;
import com.dnd.spaced.domain.auth.application.dto.response.TokenDto;
import com.dnd.spaced.domain.auth.presentation.dto.AuthControllerMapper;
import com.dnd.spaced.domain.auth.presentation.dto.request.InitProfileRequest;
import com.dnd.spaced.domain.auth.presentation.dto.response.TokenResponse;
import com.dnd.spaced.domain.auth.presentation.exception.RefreshTokenNotFoundException;
import com.dnd.spaced.global.config.properties.TokenProperties;
import com.dnd.spaced.global.controller.ResponseEntityConst;
import com.dnd.spaced.global.resolver.auth.AuthAccount;
import com.dnd.spaced.global.resolver.auth.AuthAccountInfo;
import com.dnd.spaced.global.security.handler.OAuth2SuccessHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProperties tokenProperties;

    @PostMapping("/profile")
    public ResponseEntity<Void> initProfile(
            @AuthAccount AuthAccountInfo accountInfo,
            @Valid @RequestBody InitProfileRequest request
    ) {
        authService.initProfile(accountInfo.email(), request.jobGroup(), request.company());

        return ResponseEntityConst.NO_CONTENT;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request) {
        String refreshToken = findRefreshToken(request.getCookies()).orElseThrow(RefreshTokenNotFoundException::new);
        TokenDto tokenDto = authService.refreshToken(refreshToken);
        HttpCookie cookie = ResponseCookie.from(OAuth2SuccessHandler.REFRESH_TOKEN_KEY, tokenDto.refreshToken())
                                          .httpOnly(true)
                                          .secure(true)
                                          .sameSite(SameSite.NONE.name())
                                          .maxAge(tokenProperties.refreshExpiredSeconds())
                                          .path(OAuth2SuccessHandler.DOMAIN)
                                          .build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(AuthControllerMapper.from(tokenDto));
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<Void> withdrawal(@AuthAccount AuthAccountInfo accountInfo) {
        authService.withdrawal(accountInfo.email());

        return ResponseEntityConst.NO_CONTENT;
    }

    private Optional<String> findRefreshToken(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (OAuth2SuccessHandler.REFRESH_TOKEN_KEY.equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }

        return Optional.empty();
    }
}
