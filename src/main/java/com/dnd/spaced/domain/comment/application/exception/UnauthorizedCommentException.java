package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class UnauthorizedCommentException extends BaseClientException {

    public UnauthorizedCommentException() {
        super(ExceptionCode.UNAUTHORIZED_COMMENT);
    }
}
