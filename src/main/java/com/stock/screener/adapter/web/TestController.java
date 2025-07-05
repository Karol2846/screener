package com.stock.screener.adapter.web;

import com.stock.screener.adapter.web.finhub.client.FinHubClient;
import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import com.stock.screener.adapter.web.seeking.alpha.client.SeekingAlphaClient;
import com.stock.screener.adapter.web.seeking.alpha.model.moving_average.MovingAverageRespnse;
import com.stock.screener.adapter.web.seeking.alpha.model.price_target.PriceTargetResponse;
import com.stock.screener.adapter.web.seeking.alpha.model.summary.SummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final FinHubClient finHubClient;
    private final SeekingAlphaClient seekingAlphaClient;

    @GetMapping("/symbol/{symbol}")
    public CurrentPriceResponse getCurrentPrice(@PathVariable String symbol) {
        return finHubClient.getCurrentPrice(symbol.toUpperCase());
    }

    @GetMapping("/summary/{symbols}")
    public SummaryResponse getSummary(@PathVariable String symbols) {
        return seekingAlphaClient.getSummary(symbols);
    }

    @GetMapping("/moving-average/{symbols}")
    public MovingAverageRespnse getMovingAverages(@PathVariable String symbols) {
        return seekingAlphaClient.getMovingAverage(symbols);
    }

    @GetMapping("/price/taget/{tickers}")
    public PriceTargetResponse getPriceTarget(@PathVariable String tickers) {
        return seekingAlphaClient.getPriceTarget(tickers);
    }
}
