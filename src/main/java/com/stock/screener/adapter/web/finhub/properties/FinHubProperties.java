package com.stock.screener.adapter.web.finhub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "finhub")
public record FinHubProperties(
        String apiKey,
        String baseUrl,
        String getCurrentPriceEndpoint) {
}
