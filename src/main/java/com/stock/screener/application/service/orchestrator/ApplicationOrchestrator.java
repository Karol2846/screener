package com.stock.screener.application.service.orchestrator;

import com.stock.screener.application.port.out.StockRepository;
import com.stock.screener.application.service.PriceService;
import com.stock.screener.application.service.StockSummaryService;
import com.stock.screener.domain.service.StockIdentifierMappingService;
import com.stock.screener.domain.service.SymbolFileReaderService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationOrchestrator {

    private final SymbolFileReaderService fileReader;
    private final StockRepository stockRepository;
    private final StockSummaryService stockSummaryService;
    private final StockIdentifierMappingService mappingService;
    private final PriceService priceService;
//    private final AnalystRecommendationService analystRecommendationService;
//    private final PriceTargetService priceTargetService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void performDailyUpdate() {
        log.info("Starting daily update");

        List<String> symbolsFromFile = fileReader.readSymbolsFromFile();
        List<String> newSymbols = identifyNewSymbols(symbolsFromFile);

        // Create new stocks if any
        if (!newSymbols.isEmpty()) {
            createNewStocks(newSymbols);
            mappingService.refreshCache();
        }

        // Daily updates
        priceService.processPrice(symbolsFromFile);

        log.info("Daily update completed for {} symbols", symbolsFromFile.size());
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void performMonthlyUpdate() {
        log.info("Starting monthly update");

        List<String> symbolsFromFile = fileReader.readSymbolsFromFile();

        // Monthly updates
        stockSummaryService.processStockSummaries(symbolsFromFile);
//        priceTargetService.processPriceTargets(symbolsFromFile);
//        analystRecommendationService.updateAnalystRecommendations(symbolsFromFile);

        log.info("Monthly update completed for {} symbols", symbolsFromFile.size());
    }

    private List<String> identifyNewSymbols(List<String> symbolsFromFile) {
        Set<String> existingSymbols = new HashSet<>(stockRepository.findAllSymbols());

        return symbolsFromFile.stream()
                .filter(symbol -> !existingSymbols.contains(symbol))
                .collect(Collectors.toList());
    }

    private void createNewStocks(List<String> newSymbols) {
        log.info("Creating {} new stocks: {}", newSymbols.size(), newSymbols);

        // Process new stocks via StockSummary to get all required data
        stockSummaryService.processStockSummaries(newSymbols);
    }
}