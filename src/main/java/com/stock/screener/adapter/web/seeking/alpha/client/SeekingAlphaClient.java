package com.stock.screener.adapter.web.seeking.alpha.client;

import com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation.AnalystRecommendationResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageRespnse;
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
        // FIXME: validation should be applied, but not here
//        if (symbols.split(",").length > seekingAlphaProperties.maxListSize()) {
//            throw new ListSizeExceededException("The number of symbols exceeds the maximum allowed size of " +
//                    seekingAlphaProperties.maxListSize());
//        }

        log.info("Fetching summary for symbols: [{}]", symbols);

        return seekingAlphaWebClient.get()
                .uri("/get-summary?symbols={symbols}", symbols)
                .retrieve()
                .bodyToMono(SummaryResponse.class)
                .block();
    }

    public MovingAverageRespnse getMovingAverage(String symbols) {
        log.info("Fetching moving average for symbols: [{}]", symbols);

        String fields = String.join(",", properties.momentumFields());

        log.info("Fields from properties: [{}]", fields);

        return seekingAlphaWebClient.get()
                .uri("/v2/get-momentum?symbols={symbols}&fields={fields}", symbols, fields)
                .retrieve()
                .bodyToMono(MovingAverageRespnse.class)
                .block();
    }

    public PriceTargetResponse getPriceTarget(String tickers) {

        log.info("Fetching price target for tickers: [{}]", tickers);

        return seekingAlphaWebClient.get()
                .uri("/get-analyst-price-target?ticker_ids={tickers}&group_by_month={groupByMonth}", tickers, properties.groupByMonth())
                .retrieve()
                .bodyToMono(PriceTargetResponse.class)
                .block();
    }

    public AnalystRecommendationResponse getAnalystRecommendation(String tickers) {

        log.info("Fetching analyst recommendation for tickers: [{}]", tickers);

        return seekingAlphaWebClient.get()
                .uri("/get-analyst-recommendations?ticker_ids={tickers}&group_by_month={groupByMonth}", tickers, properties.groupByMonth())
                .retrieve()
                .bodyToMono(AnalystRecommendationResponse.class)
                .block();
    }
}
