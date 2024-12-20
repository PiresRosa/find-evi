package com.dnd.spaced.domain.auth.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenAccountException extends BaseClientException {

    public ForbiddenAccountException() {
        super(ExceptionCode.FORBIDDEN_ACCOUNT);
    }
}
