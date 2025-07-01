package com.stock.screener.adapter.web.seeking.alpha.config;

import com.stock.screener.adapter.web.seeking.alpha.properties.FinHubProperties;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final FinHubProperties finHubProperties;
    private final SeekingAlphaProperties seekingAlphaProperties;

    @Bean
    @Qualifier("seekingAlphaClient")
    public WebClient seekingAlphaWebClient() {
        return WebClient.builder()
                .baseUrl(seekingAlphaProperties.baseUrl())
                .defaultHeader("x-rapidapi-host", seekingAlphaProperties.apiHost())
                .defaultHeader("x-rapidapi-key", seekingAlphaProperties.apiKey())
                .build();
    }

    @Bean
    @Qualifier("finhubClient")
    public WebClient finHubWebClient() {
        return WebClient.builder()
                .baseUrl(finHubProperties.baseUrl())
                .defaultUriVariables(Map.of("token", finHubProperties.apiKey()))
                .build();
    }
}
