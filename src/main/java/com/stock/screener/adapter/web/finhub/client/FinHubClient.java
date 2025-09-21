package com.stock.screener.adapter.web.finhub.client;

import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import com.stock.screener.adapter.web.finhub.properties.FinHubProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinHubClient {

    private final WebClient finHubWebClient;
    private final FinHubProperties properties;

    public CurrentPriceResponse getCurrentPrice(String symbol) {
        log.info("Fetching current price for symbol: {}", symbol);

        return finHubWebClient.get()
                .uri(properties.getCurrentPriceEndpoint(), symbol)
                .retrieve()
                .bodyToMono(CurrentPriceResponse.class)
                .block();
    }
}
