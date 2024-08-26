package com.letscareer.url.presentation;

import com.letscareer.url.application.UrlMetadataService;
import com.letscareer.url.dto.UrlMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RequiredArgsConstructor
@RestController
public class UrlMetadataController {

    private final UrlMetadataService urlMetadataService;

    @GetMapping("/url-metadata")
    public UrlMetadata getUrlMetadata(@RequestParam String url) throws IOException {
        return urlMetadataService.getUrlMetadata(url);
    }
}
