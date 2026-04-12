package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenLikeException extends BaseClientException {

    public ForbiddenLikeException() {
        super(ExceptionCode.FORBIDDEN_LIKE);
    }
}
