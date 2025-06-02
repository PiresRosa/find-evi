package com.dnd.spaced.global.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseEntityConst {

    public static ResponseEntity<Void> NO_CONTENT = ResponseEntity.noContent()
                                                                  .build();
}
