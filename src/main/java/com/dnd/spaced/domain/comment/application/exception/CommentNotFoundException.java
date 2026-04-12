package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class CommentNotFoundException extends BaseClientException {

    public CommentNotFoundException() {
        super(ExceptionCode.COMMENT_NOT_FOUND);
    }
}
