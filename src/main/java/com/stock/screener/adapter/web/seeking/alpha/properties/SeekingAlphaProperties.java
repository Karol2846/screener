package com.stock.screener.adapter.web.seeking.alpha.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "seeking-alpha")
public record SeekingAlphaProperties(
        String apiKey,
        String apiHost,
        String baseUrl) {
}
