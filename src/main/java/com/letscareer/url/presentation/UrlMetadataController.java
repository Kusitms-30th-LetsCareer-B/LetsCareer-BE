package com.letscareer.url.presentation;

import com.letscareer.url.application.UrlMetadataService;
import com.letscareer.url.dto.UrlMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RequiredArgsConstructor
@RestController
@RequestMapping("/url")
public class UrlMetadataController {

    private final UrlMetadataService urlMetadataService;

    @GetMapping("/metadata")
    public UrlMetadata getUrlMetadata(@RequestParam String url) {
        return urlMetadataService.getUrlMetadata(url);
    }
}
