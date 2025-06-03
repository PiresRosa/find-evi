package com.dnd.spaced.domain.auth.domain;

import com.dnd.spaced.domain.auth.infrastructure.PrivateClaims;
import java.util.Optional;

public interface TokenDecoder {

    Optional<PrivateClaims> decode(TokenType tokenType, String token);
}
