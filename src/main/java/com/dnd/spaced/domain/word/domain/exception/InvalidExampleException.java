package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidExampleException extends BaseClientException {

    public InvalidExampleException() {
        super(ExceptionCode.INVALID_EXAMPLE);
    }
}
