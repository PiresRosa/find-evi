package com.dnd.spaced.domain.account.domain.repository;

import com.dnd.spaced.domain.account.domain.Account;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findBy(String email);
}
