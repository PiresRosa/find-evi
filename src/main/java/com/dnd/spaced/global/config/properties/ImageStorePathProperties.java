package com.dnd.spaced.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.image")
public record ImageStorePathProperties(String path) {
}
