package com.dnd.spaced.domain.account.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidCompanyException extends BaseClientException {

    public InvalidCompanyException() {
        super(ExceptionCode.INVALID_COMPANY);
    }
}
