package com.stock.screener.application.service;

import static com.stock.screener.application.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.domain.mapper.PriceHistoryMapper;
import com.stock.screener.application.domain.model.PriceHistory;
import com.stock.screener.application.port.in.PriceHistoryUseCase;
import com.stock.screener.application.port.in.api.FinHubApi;
import com.stock.screener.application.port.out.PriceHistoryRepository;
import com.stock.screener.application.service.helper.MovingAveragesHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceHistoryService implements PriceHistoryUseCase {

    private final PriceHistoryMapper priceMapper;
    private final PriceHistoryRepository priceRepository;
    private final FinHubApi finHubApi;
    private final MovingAveragesHelper movingAveragesHelper;

    @Override
    public void updatePriceHistories(List<String> symbols) {
        log.info("Updating price history for symbols: {}", symbols);

        List<PriceHistory> prices = symbols.stream()
                .map(this::getOrCreatePriceHistory)
                .toList();

        prices.forEach(this::updateCurrentPrice);
        movingAveragesHelper.updateMovingAverages(prices);

        priceRepository.saveAll(prices);
        log.info("Price history update completed for {} symbols.", symbols.size());
    }

    private PriceHistory getOrCreatePriceHistory(String symbol) {
        log.debug("Fetching priceHistory for symbol: {}", symbol);

        return priceRepository.findById(forSymbolWithActualDate(symbol))
                .orElseGet(() -> createNewPriceHistory(symbol));
    }

    private static PriceHistory createNewPriceHistory(String symbol) {
        log.debug("PriceHistory for symbol: {} not found, creating new entity", symbol);
        return PriceHistory.fromSymbol(symbol);
    }

    private void updateCurrentPrice(PriceHistory priceHistory) {
        log.debug("Fetching current price for symbol: {}", priceHistory.getId().getSymbol());
        var command = finHubApi.getCurrentPrice(priceHistory.getId().getSymbol());
        log.debug("Current price for symbol: {} is: {}", command.ticker(), command.currentPrice());

        priceMapper.update(priceHistory, command);
        log.debug("Updated current price for symbol: {}, to: {}", priceHistory.getId().getSymbol(), priceHistory);
    }
}
