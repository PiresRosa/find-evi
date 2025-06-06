package com.dnd.spaced.domain.account.application;

import com.dnd.spaced.domain.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ValidateAccountService {

    private final AccountRepository accountRepository;

    public boolean isValidAccount(String email) {
        return accountRepository.findBy(email)
                                .isPresent();
    }
}
