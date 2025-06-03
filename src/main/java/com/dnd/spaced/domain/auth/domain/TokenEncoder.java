package com.dnd.spaced.domain.auth.domain;

import java.time.LocalDateTime;
import java.util.Map;

public interface TokenEncoder {

    String encode(LocalDateTime targetTime, TokenType tokenType, Map<String, Object> attributes);
}
