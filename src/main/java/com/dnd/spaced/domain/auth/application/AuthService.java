package com.dnd.spaced.domain.auth.application;

import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import com.dnd.spaced.domain.auth.application.dto.AuthServiceMapper;
import com.dnd.spaced.domain.auth.application.dto.response.TokenDto;
import com.dnd.spaced.domain.auth.application.exception.ExpiredTokenException;
import com.dnd.spaced.domain.auth.application.exception.ForbiddenAccountException;
import com.dnd.spaced.domain.auth.domain.TokenDecoder;
import com.dnd.spaced.domain.auth.domain.TokenEncoder;
import com.dnd.spaced.domain.auth.domain.TokenType;
import com.dnd.spaced.domain.auth.infrastructure.JwtDecoder;
import com.dnd.spaced.domain.auth.infrastructure.PrivateClaims;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final TokenEncoder tokenEncoder;
    private final TokenDecoder tokenDecoder;
    private final AccountRepository accountRepository;

    @Transactional
    public void saveCareerInfo(String email, String jobGroup, String company, String experience) {
        Account account = findAccount(email);

        account.changeCareerInfo(jobGroup, company, experience);
    }

    public TokenDto refreshToken(String refreshToken) {
        PrivateClaims privateClaims = tokenDecoder.decode(TokenType.REFRESH, refreshToken)
                                                  .orElseThrow(ExpiredTokenException::new);
        String generateAccessToken = tokenEncoder.encode(
                LocalDateTime.now(),
                TokenType.ACCESS,
                Map.of(JwtDecoder.CLAIM_EMAIL, privateClaims.email(), JwtDecoder.CLAIM_ROLE, privateClaims.roleName())
        );
        String generateRefreshToken = tokenEncoder.encode(
                LocalDateTime.now(),
                TokenType.REFRESH,
                Map.of(JwtDecoder.CLAIM_EMAIL, privateClaims.email(), JwtDecoder.CLAIM_ROLE, privateClaims.roleName())
        );

        return AuthServiceMapper.of(generateAccessToken, generateRefreshToken);
    }

    @Transactional
    public void withdrawal(String email) {
        Account account = findAccount(email);

        account.withdrawal();
    }

    private Account findAccount(String email) {
        return accountRepository.findBy(email)
                                .orElseThrow(ForbiddenAccountException::new);
    }
}
