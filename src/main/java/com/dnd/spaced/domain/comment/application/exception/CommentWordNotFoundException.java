package com.dnd.spaced.domain.comment.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class CommentWordNotFoundException extends BaseClientException {

    public CommentWordNotFoundException() {
        super(ExceptionCode.COMMENT_WORD_NOT_FOUND);
    }
}
