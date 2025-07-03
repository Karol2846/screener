package com.stock.screener.adapter.web.finhub.client;

import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinHubClient {

    private final WebClient finHubWebClient;

    public CurrentPriceResponse getCurrentPrice(String symbol) {
        log.info("Fetching current price for symbol: {}", symbol);

        return finHubWebClient.get()
                .uri("/quote?symbol={symbol}", symbol)
                .retrieve()
                .bodyToMono(CurrentPriceResponse.class)
                .block();
    }
}
