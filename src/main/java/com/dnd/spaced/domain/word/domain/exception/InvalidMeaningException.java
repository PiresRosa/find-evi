package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidMeaningException extends BaseClientException {

    public InvalidMeaningException() {
        super(ExceptionCode.INVALID_MEANING);
    }
}
