package com.dnd.spaced.global.security.attribute;

import com.dnd.spaced.global.security.attribute.exception.InvalidOAuth2TypeException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;

public enum OAuth2AttributeConverter {
    GOOGLE(
            "google",
            (attributeKey, attributes) -> GoogleOAuth2Attribute.builder()
                                                               .attributeKey(attributeKey)
                                                               .attributes(attributes)
                                                               .build()
    );

    private final String type;
    private final BiFunction<String, Map<String, Object>, AbstractOAuth2Attribute> attributeBiFunction;

    OAuth2AttributeConverter(
            String type,
            BiFunction<String, Map<String, Object>, AbstractOAuth2Attribute> attributeBiFunction
    ) {
        this.type = type;
        this.attributeBiFunction = attributeBiFunction;
    }

    public static AbstractOAuth2Attribute convert(String type, Map<String, Object> attributes, String attributeKey) {
        return Arrays.stream(OAuth2AttributeConverter.values())
                     .filter(converter -> converter.type.equalsIgnoreCase(type))
                     .findAny()
                     .map(converter -> converter.attributeBiFunction.apply(attributeKey, attributes))
                     .orElseThrow(InvalidOAuth2TypeException::new);
    }
}
