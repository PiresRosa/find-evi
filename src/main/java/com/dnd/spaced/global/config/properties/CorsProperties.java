package com.dnd.spaced.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cors")
public record CorsProperties(String allowedOrigin, long maxAge) {
}
