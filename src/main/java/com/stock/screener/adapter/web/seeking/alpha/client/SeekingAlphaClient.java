package com.stock.screener.adapter.web.seeking.alpha.client;

import com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation.AnalystRecommendationResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.price_target.PriceTargetResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.summary.SummaryResponse;
import com.stock.screener.adapter.web.seeking.alpha.properties.SeekingAlphaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeekingAlphaClient {

    private final WebClient seekingAlphaWebClient;
    private final SeekingAlphaProperties properties;


    public SummaryResponse getSummary(String symbols) {
        log.info("Fetching summary for symbols: [{}]", symbols);

        return seekingAlphaWebClient.get()
                .uri(properties.getSummaryEndpoint(), symbols)
                .retrieve()
                .bodyToMono(SummaryResponse.class)
                .block();
    }

    public MovingAverageResponse getMovingAverage(String symbols) {
        log.info("Fetching moving average for symbols: [{}]", symbols);

        String fields = String.join(",", properties.momentumFields());

        return seekingAlphaWebClient.get()
                .uri(properties.getMovingAverageEndpoint(), symbols, fields)
                .retrieve()
                .bodyToMono(MovingAverageResponse.class)
                .block();
    }

    public PriceTargetResponse getPriceTarget(String tickers) {

        log.info("Fetching price target for tickers: [{}]", tickers);

        return seekingAlphaWebClient.get()
                .uri(properties.getPriceTargetEndpoint(), tickers, properties.groupByMonth())
                .retrieve()
                .bodyToMono(PriceTargetResponse.class)
                .block();
    }

    public AnalystRecommendationResponse getAnalystRecommendation(String tickers) {

        log.info("Fetching analyst recommendation for tickers: [{}]", tickers);

        return seekingAlphaWebClient.get()
                .uri(properties.getAnalystRecomendationEndpoint(), tickers, properties.groupByMonth())
                .retrieve()
                .bodyToMono(AnalystRecommendationResponse.class)
                .block();
    }
}
