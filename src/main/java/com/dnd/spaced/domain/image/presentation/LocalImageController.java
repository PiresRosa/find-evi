package com.dnd.spaced.domain.image.presentation;

import com.dnd.spaced.domain.image.application.LocalImageService;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class LocalImageController {

    private final LocalImageService localImageService;

    @GetMapping("/{name}")
    public ResponseEntity<Resource> readImage(@PathVariable String name) throws MalformedURLException {
        Resource resource = localImageService.readImage(name);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(resource);
    }
}
