package com.dnd.spaced.domain.comment.domain.repository.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class UnsupportedCommentSortConditionException extends BaseClientException {

    public UnsupportedCommentSortConditionException() {
        super(ExceptionCode.UNSUPPORTED_COMMENT_SORT_CONDITION);
    }
}
