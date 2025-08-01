package com.stock.screener.domain.service;

import com.stock.screener.application.port.out.StockRepository;
import com.stock.screener.domain.model.Stock;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockIdentifierMappingService {

    private final StockRepository stockRepository;
    private final Map<Integer, String> tickerIdToSymbolMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeCache() {
        refreshCache();
    }

    public void refreshCache() {
        tickerIdToSymbolMap.clear();

        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(stock -> {
            if (stock.getSeekingAlphaTrackerId() != null) {
                tickerIdToSymbolMap.put(stock.getSeekingAlphaTrackerId(), stock.getSymbol());
            }
        });

        log.info("Refreshed mapping cache with {} entries", tickerIdToSymbolMap.size());
    }

    public Optional<String> getSymbolByTickerId(Integer tickerId) {
        return Optional.ofNullable(tickerIdToSymbolMap.get(tickerId));
    }

    public Optional<Integer> getTickerIdBySymbol(String symbol) {
        return tickerIdToSymbolMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(symbol))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public void updateMapping(String symbol, Integer tickerId) {
        tickerIdToSymbolMap.put(tickerId, symbol);
    }
}
