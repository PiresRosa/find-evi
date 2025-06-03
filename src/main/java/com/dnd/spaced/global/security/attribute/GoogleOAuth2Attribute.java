package com.dnd.spaced.global.security.attribute;

import java.util.Map;
import lombok.Builder;

public class GoogleOAuth2Attribute extends AbstractOAuth2Attribute {

    private static final String ATTRIBUTE_EMAIL_KEY = "email";

    @Builder
    private GoogleOAuth2Attribute(String attributeKey, Map<String, Object> attributes) {
        this.attributeKey = attributeKey;
        this.email = (String) attributes.get(ATTRIBUTE_EMAIL_KEY);
    }
}
