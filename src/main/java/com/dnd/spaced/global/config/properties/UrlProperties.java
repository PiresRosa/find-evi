package com.dnd.spaced.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.url")
public record UrlProperties(String baseUrl, String imageUri) {
}
