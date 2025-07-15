package com.stock.screener.application.service.helper;

import com.stock.screener.application.domain.mapper.PriceHistoryMapper;
import com.stock.screener.application.domain.model.PriceHistory;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovingAveragesHelper {

    private final PriceHistoryMapper priceMapper;
    private final SeekingAlphaApi seekingAlphaApi;

    public void updateMovingAverages(List<PriceHistory> prices) {
        var commands = getMovingAveragesCommands(prices);
        log.debug("Current moving average data: {}", commands);

        for (int i = 0; i < commands.size(); i++) {
            priceMapper.update(prices.get(i), commands.get(i));
            log.debug("Updated moving averages for symbol: {}", prices.get(i).getId().getSymbol());
        }
    }

    private List<MovingAveragesCommand> getMovingAveragesCommands(List<PriceHistory> prices) {
        List<String> priceStockSymbols = prices.stream()
                .map(price -> price.getId().getSymbol())
                .toList();
        log.debug("Fetching moving averages for symbols: {}", priceStockSymbols);
        return seekingAlphaApi.getMovingAverages(priceStockSymbols);
    }
}
