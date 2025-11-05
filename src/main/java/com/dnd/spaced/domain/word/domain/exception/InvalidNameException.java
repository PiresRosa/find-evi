package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidNameException extends BaseClientException {

    public InvalidNameException() {
        super(ExceptionCode.INVALID_NAME);
    }
}
