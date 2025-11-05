package com.dnd.spaced.domain.word.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenBookmarkException extends BaseClientException {

    public ForbiddenBookmarkException() {
        super(ExceptionCode.FORBIDDEN_BOOKMARK);
    }
}
