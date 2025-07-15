package com.stock.screener.application.port.in;

import com.stock.screener.application.domain.model.Stock;

import java.util.List;

public interface StockUseCase {

    void saveStock(List<String> symbols);

    List<Stock> findStocks(List<String> symbols);

    List<Stock> findStocks(String symbols);
}
