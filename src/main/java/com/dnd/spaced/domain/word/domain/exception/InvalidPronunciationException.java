package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidPronunciationException extends BaseClientException {

    public InvalidPronunciationException() {
        super(ExceptionCode.INVALID_PRONUNCIATION);
    }
}
