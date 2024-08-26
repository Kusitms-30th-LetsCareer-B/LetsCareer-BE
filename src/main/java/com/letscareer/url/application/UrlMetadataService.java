package com.letscareer.url.application;

import com.letscareer.url.dto.UrlMetadata;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class UrlMetadataService {

    public UrlMetadata getUrlMetadata(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String description = doc.select("meta[name=description]").attr("content");
            String iconUrl = doc.select("link[rel~=(?i)^(shortcut|icon|mask-icon|apple-touch-icon)]").attr("href");

            // 절대 경로로 아이콘 URL 처리
            iconUrl = resolveIconUrl(url, iconUrl);

            return new UrlMetadata(title, description, iconUrl, url);
        } catch (IOException e) {
            log.error("Failed to fetch URL metadata for: " + url, e);
            return null;
        }
    }

    private String resolveIconUrl(String pageUrl, String iconUrl) throws IOException {
        // 만약 iconUrl이 비어있거나 상대 경로라면 절대 경로로 변환
        if (!StringUtils.hasText(iconUrl) || iconUrl.startsWith("/")) {
            URL url = new URL(pageUrl);
            String baseUrl = url.getProtocol() + "://" + url.getHost();
            iconUrl = baseUrl + (iconUrl.startsWith("/") ? iconUrl : "/" + iconUrl);
        }

        // 아이콘 URL에 대한 유효성 검사 (404 확인)
        if (isInvalidImageUrl(iconUrl)) {
            iconUrl = pageUrl + "/favicon.ico";
            if (isInvalidImageUrl(iconUrl)) {
                iconUrl = null;
            }
        }

        return iconUrl;
    }

    private boolean isInvalidImageUrl(String iconUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(iconUrl).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode != HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return true;
        }
    }
}
