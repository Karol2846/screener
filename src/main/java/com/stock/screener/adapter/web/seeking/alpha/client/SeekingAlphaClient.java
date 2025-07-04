package com.stock.screener.adapter.web.seeking.alpha.client;

import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageRespnse;
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
    private final SeekingAlphaProperties seekingAlphaProperties;


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

        String fields = String.join(",", seekingAlphaProperties.momentumFields());

        log.info("Fields from properties: [{}]", fields);

        return seekingAlphaWebClient.get()
                .uri("/v2/get-momentum?symbols={symbols}&fields={fields}", symbols, fields)
                .retrieve()
                .bodyToMono(MovingAverageRespnse.class)
                .block();
    }
}
