package com.dnd.spaced.domain.account.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class AccountUnauthorizedException extends BaseClientException {

    public AccountUnauthorizedException() {
        super(ExceptionCode.ACCOUNT_UNAUTHORIZED);
    }
}
