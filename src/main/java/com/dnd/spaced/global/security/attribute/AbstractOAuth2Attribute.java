package com.dnd.spaced.global.security.attribute;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractOAuth2Attribute {

    protected String attributeKey;
    protected String email;

    public Map<String, Object> convert() {
        Map<String, Object> map = new HashMap<>();

        map.put("sub", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);

        return map;
    }
}
