package com.dnd.spaced.domain.bookmark.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class BookmarkWordNotFoundException extends BaseClientException {

    public BookmarkWordNotFoundException() {
        super(ExceptionCode.BOOKMARK_WORD_NOT_FOUND);
    }
}
