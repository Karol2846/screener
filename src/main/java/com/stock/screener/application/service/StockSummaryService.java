package com.stock.screener.application.service;

import com.stock.screener.application.event.DomainEventPublisher;
import com.stock.screener.application.event.model.StockSummaryEvent;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockSummaryService {

    private final SeekingAlphaApi seekingAlphaApi;
    private final DomainEventPublisher eventPublisher;

    public void processStockSummaries(List<String> symbols) {
        List<StockSummaryCommand> summaries = seekingAlphaApi.getStockSummaries(symbols);
        log.info("Processing stock summaries: {}", summaries);
        //TODO: add fundamentalData listener
        summaries.forEach(summary ->
                eventPublisher.publish(new StockSummaryEvent(summary)));
    }
}
