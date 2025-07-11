package com.stock.screener.application.port.in;

import com.stock.screener.application.domain.model.Stock;

import java.util.List;

public interface PriceHistoryUseCase {

    void updateCurrentPrice(List<Stock> stocks);

    void updateCurrentPriceBySymbol(List<String> symbols);
}
