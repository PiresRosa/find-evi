package com.dnd.spaced.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseClientException extends IllegalArgumentException {

    private final ExceptionCode code;
}
