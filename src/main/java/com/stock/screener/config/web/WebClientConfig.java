package com.stock.screener.config.web;

import com.stock.screener.adapter.web.finhub.properties.FinHubProperties;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private static final String TOKEN_QUERY_PARAMETER = "token";

    private final FinHubProperties finHubProperties;
    private final SeekingAlphaProperties seekingAlphaProperties;

    @Bean
    @Qualifier("seekingAlphaWebClient")
    public WebClient seekingAlphaWebClient() {
        return WebClient.builder()
                .baseUrl(seekingAlphaProperties.baseUrl())
                .defaultHeader("x-rapidapi-host", seekingAlphaProperties.apiHost())
                .defaultHeader("x-rapidapi-key", seekingAlphaProperties.apiKey())
                .build();
    }

    @Bean
    @Qualifier("finHubWebClient")
    public WebClient finHubWebClient() {
        return WebClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory(uriComponentsBuilder()))
                .build();
    }

    private UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(finHubProperties.baseUrl())
                .queryParam(TOKEN_QUERY_PARAMETER, finHubProperties.apiKey());
    }
}
