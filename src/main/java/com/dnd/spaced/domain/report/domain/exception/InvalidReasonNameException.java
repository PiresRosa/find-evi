package com.dnd.spaced.domain.report.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidReasonNameException extends BaseClientException {

    public InvalidReasonNameException() {
        super(ExceptionCode.INVALID_REASON_NAME);
    }
}
