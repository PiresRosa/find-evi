package com.dnd.spaced.domain.quiz.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class NotEnoughQuestionsException extends BaseClientException {

    public NotEnoughQuestionsException() {super(ExceptionCode.NOT_ENOUGH_QUESTIONS_FOR_CATEGORY);}
}
