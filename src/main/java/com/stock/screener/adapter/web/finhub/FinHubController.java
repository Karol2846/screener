package com.stock.screener.adapter.web.finhub;

import com.stock.screener.adapter.web.finhub.client.FinHubClient;
import com.stock.screener.adapter.web.finhub.model.current_price.CurrentPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FinHubController {

    private final FinHubClient finHubClient;

    @GetMapping("/symbol/{symbol}")
    public CurrentPriceResponse getCurrentPrice(@PathVariable String symbol) {
        return finHubClient.getCurrentPrice(symbol.toUpperCase());
    }
}
