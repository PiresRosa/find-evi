package com.dnd.spaced.global.resolver.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class AccountUnauthorizedException extends BaseClientException {

    public AccountUnauthorizedException() {
        super(ExceptionCode.UNAUTHORIZED);
    }
}
