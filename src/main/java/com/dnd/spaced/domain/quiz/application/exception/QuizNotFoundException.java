package com.dnd.spaced.domain.quiz.application.exception;

import com.dnd.spaced.global.exception.BaseClientException;
import com.dnd.spaced.global.exception.ExceptionCode;

public class QuizNotFoundException extends BaseClientException {

    public QuizNotFoundException() {super(ExceptionCode.QUIZ_NOT_FOUND);}
}
