package com.dnd.spaced.domain.image.application;

import com.dnd.spaced.global.config.properties.ImageStorePathProperties;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalImageService {

    private static final String FILE_PROTOCOL_PREFIX = "file:";

    private final ImageStorePathProperties imageStorePathProperties;

    public Resource readImage(String name) throws MalformedURLException {
        return new UrlResource(FILE_PROTOCOL_PREFIX + imageStorePathProperties.path() + name);
    }
}
