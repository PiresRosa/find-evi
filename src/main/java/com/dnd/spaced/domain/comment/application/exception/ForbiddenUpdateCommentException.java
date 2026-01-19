package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class ForbiddenUpdateCommentException extends BaseClientException {

    public ForbiddenUpdateCommentException() {
        super(ExceptionCode.FORBIDDEN_UPDATE_COMMENT);
    }
}
