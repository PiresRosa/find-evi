package com.dnd.spaced.domain.auth.infrastructure.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidTokenException extends BaseClientException {

    public InvalidTokenException() {
        super(ExceptionCode.INVALID_TOKEN);
    }
}
