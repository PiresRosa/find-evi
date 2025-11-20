package com.dnd.spaced.domain.comment.domain.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class InvalidContentException extends BaseClientException {

    public InvalidContentException() {
        super(ExceptionCode.INVALID_CONTENT);
    }
}
