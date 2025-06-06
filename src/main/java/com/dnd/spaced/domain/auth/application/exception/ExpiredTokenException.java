package com.dnd.spaced.domain.auth.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ExpiredTokenException extends BaseClientException {

    public ExpiredTokenException() {
        super(ExceptionCode.EXPIRED_TOKEN);
    }
}
