package com.dnd.spaced.domain.account.domain.repository;

import static com.dnd.spaced.domain.account.domain.QAccount.account;

import com.dnd.spaced.domain.account.domain.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuerydslAccountRepository implements AccountRepository {

    private final JPAQueryFactory queryFactory;
    private final AccountCrudRepository accountCrudRepository;

    @Override
    public Account save(Account account) {
        return accountCrudRepository.save(account);
    }

    @Override
    public Optional<Account> findBy(String email) {
        Account result = queryFactory.selectFrom(account)
                                      .where(account.email.eq(email))
                                      .fetchOne();

        return Optional.ofNullable(result);
    }
}
