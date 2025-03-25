package com.dnd.spaced.global.config.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.profile.nickname")
public record NicknameProperties(List<String> adjective, List<String> planet, String format) {

    public String generate() {
        int adjectiveIndex = generateRandomIndex(adjective.size());
        int planetIndex = generateRandomIndex(planet.size());

        return adjective.get(adjectiveIndex).concat(planet().get(planetIndex));
    }

    public String format(String nickname, long count) {
        return String.format(format, nickname, count);
    }

    private static int generateRandomIndex(int range) {
        return (int) (Math.random() * range);
    }
}
