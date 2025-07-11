package com.stock.screener.application.service;

import com.stock.screener.application.domain.mapper.StockMapper;
import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.StockUseCase;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import com.stock.screener.application.port.out.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService implements StockUseCase {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;
    private final SeekingAlphaApi seekingAlphaApi;

    @Override
    public List<Stock> saveStock(String symbols) {

        List<StockSummaryCommand> stockSummaryCommand = seekingAlphaApi.getStockSummary(symbols);
        log.info("Retrived {} stocks: {}", stockSummaryCommand.size(), stockSummaryCommand);

        var stocks = stockSummaryCommand.stream()
                .filter(stock -> !stockRepository.existsBySymbol(stock.ticker()))
                .map(stockMapper::mapToStock)
                .toList();

        log.info("Saving stocks: {}", stocks);
        List<Stock> savedstocks = stockRepository.saveAll(stocks);
        log.info("Saved stocks: {}", savedstocks);

        List<String> symbolList = Arrays.asList(Strings.splitList(symbols));
        log.info("Retriving stocks for symbols: [{}]", symbolList);
        return stockRepository.findAllBySymbolIn(symbolList);
    }
}
