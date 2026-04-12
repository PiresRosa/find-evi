package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenDeleteCommentException extends BaseClientException {

    public ForbiddenDeleteCommentException() {
        super(ExceptionCode.FORBIDDEN_DELETE_COMMENT);
    }
}
