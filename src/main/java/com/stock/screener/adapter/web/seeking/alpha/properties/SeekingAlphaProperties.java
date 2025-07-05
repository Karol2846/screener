package com.stock.screener.adapter.web.seeking.alpha.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "seeking-alpha")
public record SeekingAlphaProperties(
        String apiKey,
        String apiHost,
        String baseUrl,
        int maxListSize,
        List<String> momentumFields,
        boolean groupByMonth) {
}
