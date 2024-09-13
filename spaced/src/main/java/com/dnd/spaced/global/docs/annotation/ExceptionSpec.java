package com.dnd.spaced.global.docs.annotation;

import com.dnd.spaced.global.exception.ExceptionCode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionSpec {

    ExceptionCode[] values();
}
