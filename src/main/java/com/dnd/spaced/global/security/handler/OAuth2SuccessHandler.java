package com.dnd.spaced.global.security.handler;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.NicknameMetadata;
import com.dnd.spaced.domain.account.domain.Role;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.account.domain.repository.NicknameMetadataRepository;
import com.dnd.spaced.domain.auth.domain.TokenEncoder;
import com.dnd.spaced.domain.auth.domain.TokenType;
import com.dnd.spaced.domain.auth.infrastructure.JwtDecoder;
import com.dnd.spaced.global.config.properties.NicknameProperties;
import com.dnd.spaced.global.config.properties.ProfileImageProperties;
import com.dnd.spaced.global.config.properties.TokenProperties;
import com.dnd.spaced.global.security.dto.response.LoginResponse;
import com.dnd.spaced.global.security.exception.InvalidResponseWriteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_KEY = "refreshToken";
    public static final String DOMAIN = "/";

    private static final String ATTRIBUTE_EMAIL_KEY = "email";

    private final TokenEncoder tokenEncoder;
    private final ObjectMapper objectMapper;
    private final TokenProperties tokenProperties;
    private final NicknameProperties nicknameProperties;
    private final ProfileImageProperties profileImageProperties;
    private final AccountRepository accountRepository;
    private final NicknameMetadataRepository nicknameMetadataRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes()
                                          .get(ATTRIBUTE_EMAIL_KEY);


        accountRepository.findBy(email)
                         .ifPresentOrElse(
                                 account -> login(response, account, false),
                                 () -> login(response, signUp(email), true)
                         );

    }

    private void login(HttpServletResponse response, Account account, boolean isSignUp) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(JwtDecoder.CLAIM_EMAIL, account.getEmail());
        claims.put(JwtDecoder.CLAIM_ROLE, account.getRole().name());

        String accessToken = tokenEncoder.encode(LocalDateTime.now(), TokenType.ACCESS, claims);
        String refreshToken = tokenEncoder.encode(LocalDateTime.now(), TokenType.REFRESH, claims);

        writeResponse(response, accessToken, isSignUp);
        createRefreshTokenCookie(response, refreshToken);
    }

    private void writeResponse(HttpServletResponse response, String accessToken, boolean isSignUp) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/home")
                    .queryParam("accessToken", accessToken)
                    .queryParam("isSignUp", isSignUp)
                    .build().toUriString();

            response.sendRedirect(redirectUrl);
            PrintWriter writer = response.getWriter();

            writer.println(objectMapper.writeValueAsString(new LoginResponse(accessToken, isSignUp)));
            writer.flush();
        } catch (IOException e) {
            throw new InvalidResponseWriteException();
        }
    }

    private void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_KEY, URLEncoder.encode(refreshToken, StandardCharsets.UTF_8));

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(tokenProperties.refreshExpiredSeconds());
        cookie.setPath(DOMAIN);
        cookie.setAttribute("SameSite", "None");

        response.addCookie(cookie);
    }

    private Account signUp(String email) {
        String nickname = nicknameProperties.generate();
        String profileImage = profileImageProperties.find();

        return nicknameMetadataRepository.findBy(nickname)
                                         .map(
                                                 nicknameMetadata -> {
                                                     nicknameMetadata.addCount();
                                                     return saveAccount(email, profileImage, nicknameMetadata);
                                                 }
                                         )
                                         .orElseGet(
                                                 () -> {
                                                     NicknameMetadata nicknameMetadata = new NicknameMetadata(nickname);

                                                     nicknameMetadataRepository.save(nicknameMetadata);
                                                     return saveAccount(email, profileImage, nicknameMetadata);
                                                 }
                                         );
    }

    private Account saveAccount(String email, String profileImage, NicknameMetadata nicknameMetadata) {
        String nickname = nicknameProperties.format(
                nicknameMetadata.getNickname(),
                nicknameMetadata.getCount()
        );
        Account account = Account.builder()
                                 .email(email)
                                 .nickname(nickname)
                                 .roleName(Role.ROLE_USER.name())
                                 .profileImage(profileImage)
                                 .build();

        return accountRepository.save(account);
    }
}
