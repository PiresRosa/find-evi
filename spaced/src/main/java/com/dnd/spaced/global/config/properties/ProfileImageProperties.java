package com.dnd.spaced.global.config.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.profile.image")
public record ProfileImageProperties(List<String> name) {

    public String find() {
        int profileImageIndex = generateRandomIndex(name.size());

        return name.get(profileImageIndex);
    }

    private static int generateRandomIndex(int range) {
        return (int) (Math.random() * range);
    }
}
