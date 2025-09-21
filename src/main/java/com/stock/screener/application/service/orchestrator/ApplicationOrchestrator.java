package com.stock.screener.application.service.orchestrator;

import com.stock.screener.application.port.out.StockRepository;
import com.stock.screener.application.service.AnalystRecommendationService;
import com.stock.screener.application.service.PriceService;
import com.stock.screener.application.service.PriceTargetService;
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
    private final AnalystRecommendationService analystRecommendationService;
    private final PriceTargetService priceTargetService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void performDailyUpdate() {
        log.info("Starting daily update");

        List<String> symbolsFromFile = fileReader.readSymbolsFromFile();
        List<String> newSymbols = identifyNewSymbols(symbolsFromFile);
        var symbols = symbolsFromFile.stream()
                .filter(symbol -> !newSymbols.contains(symbol))
                .toList();


        for (int i = 0; i < symbolsFromFile.size(); i += 4) {
            List<String> part = getListPart(symbolsFromFile, i);
            priceService.processPrice(part);
        }

        log.info("Daily update completed for {} symbols", symbols.size());
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void performMonthlyUpdate() {
        log.info("Starting monthly update");

        List<String> symbolsFromFile = fileReader.readSymbolsFromFile();
        List<String> newSymbols = identifyNewSymbols(symbolsFromFile);
        if (!newSymbols.isEmpty()) {
            createNewStocks(newSymbols);
            mappingService.refreshCache();
        }

        for (int i = 0; i < symbolsFromFile.size(); i += 4) {
            List<String> part = getListPart(symbolsFromFile, i);
            stockSummaryService.processStockSummaries(part);
            priceTargetService.processPriceTargets(part);
            analystRecommendationService.processAnalystRecommendations(part);
        }

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

        for (int i = 0; i < newSymbols.size(); i += 4) {
            List<String> part = getListPart(newSymbols, i);
            stockSummaryService.processStockSummaries(part);
        }
    }

    private static List<String> getListPart(List<String> newSymbols, int i) {
        return newSymbols.subList(i, Math.min(i + 4, newSymbols.size()));
    }
}