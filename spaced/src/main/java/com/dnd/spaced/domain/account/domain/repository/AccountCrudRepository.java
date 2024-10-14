package com.dnd.spaced.domain.account.domain.repository;

import com.dnd.spaced.domain.account.domain.Account;
import org.springframework.data.repository.CrudRepository;

interface AccountCrudRepository extends CrudRepository<Account, Long> {
}
