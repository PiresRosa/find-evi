package com.dnd.spaced.domain.word.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class WordNotFoundException extends BaseClientException {

    public WordNotFoundException() {
        super(ExceptionCode.WORD_NOT_FOUND);
    }
}
