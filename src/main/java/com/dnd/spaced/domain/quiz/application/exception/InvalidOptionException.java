package com.dnd.spaced.domain.quiz.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidOptionException extends BaseClientException {

    public InvalidOptionException() {
        super(ExceptionCode.INVALID_OPTION);
    }
}
