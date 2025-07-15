package com.stock.screener.application.port.in;

import java.util.List;

public interface PriceHistoryUseCase {

    void updatePriceHistories(List<String> symbols);
}
