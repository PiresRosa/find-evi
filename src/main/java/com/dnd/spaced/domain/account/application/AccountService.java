package com.dnd.spaced.domain.account.application;

import com.dnd.spaced.domain.account.application.dto.AccountServiceMapper;
import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;
import com.dnd.spaced.domain.account.application.exception.AccountUnauthorizedException;
import com.dnd.spaced.domain.account.domain.Account;
import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void changeProfileInfo(String email, String nickname, String profileImage) {
        Account account = findAccount(email);

        account.changeProfileInfo(nickname, profileImage);
    }

    public ReadAccountInfoDto findBy(String email) {
        Account account = findAccount(email);

        return AccountServiceMapper.from(account);
    }

    private Account findAccount(String email) {
        return accountRepository.findBy(email)
                                .orElseThrow(AccountUnauthorizedException::new);
    }
}
