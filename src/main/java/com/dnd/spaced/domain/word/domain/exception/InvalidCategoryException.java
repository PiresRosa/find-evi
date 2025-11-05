package com.dnd.spaced.domain.word.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidCategoryException extends BaseClientException {

    public InvalidCategoryException() {
        super(ExceptionCode.INVALID_CATEGORY);
    }
}
