package com.stock.screener.adapter.web;

import com.stock.screener.adapter.web.finhub.client.FinHubClient;
import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import com.stock.screener.adapter.web.seeking.alpha.client.SeekingAlphaClient;
import com.stock.screener.adapter.web.seeking.alpha.model.analyst_recomendation.AnalystRecommendationResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.price_target.PriceTargetResponse;
import com.stock.screener.application.port.in.StockUseCase;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final FinHubClient finHubClient;
    private final SeekingAlphaClient seekingAlphaClient;
    private final StockUseCase stockUseCase;

    @GetMapping("/price/{symbol}")
    public CurrentPriceResponse getCurrentPrice(@PathVariable String symbol) {
        return finHubClient.getCurrentPrice(symbol.toUpperCase());
    }

    @GetMapping("/summary/{symbols}")
    public void getSummary(@PathVariable String symbols) {
        stockUseCase.saveStock(Arrays.stream(symbols.split(",")).toList());
    }

    @GetMapping("/moving-average/{symbols}")
    public MovingAverageResponse getMovingAverages(@PathVariable String symbols) {
        return seekingAlphaClient.getMovingAverage(symbols);
    }

    @GetMapping("/price-taget/{tickers}")
    public PriceTargetResponse getPriceTarget(@PathVariable String tickers) {
        return seekingAlphaClient.getPriceTarget(tickers);
    }

    @GetMapping("/analyst-recommendations/{tickers}")
    public AnalystRecommendationResponse getAnalystRecommendations(@PathVariable String tickers) {
        return seekingAlphaClient.getAnalystRecommendation(tickers);
    }
}
