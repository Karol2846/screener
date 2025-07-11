package com.stock.screener.application.port.in;

import com.stock.screener.application.domain.model.Stock;

import java.util.List;

public interface StockUseCase {

    List<Stock> saveStock(String symbols);
}
