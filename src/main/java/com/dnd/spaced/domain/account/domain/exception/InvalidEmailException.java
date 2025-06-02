package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseServerException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidEmailException extends BaseServerException {

    public InvalidEmailException() {
        super(ExceptionCode.INVALID_EMAIL);
    }
}
