package com.dnd.spaced.domain.account.application.dto;

import com.dnd.spaced.domain.account.application.dto.response.ReadAccountInfoDto;
import com.dnd.spaced.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountServiceMapper {

    public static ReadAccountInfoDto from(Account account) {
        return ReadAccountInfoDto.from(account);
    }
}
