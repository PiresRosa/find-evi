package com.dnd.spaced.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseServerException extends IllegalStateException {

    private final ExceptionCode code;
}
