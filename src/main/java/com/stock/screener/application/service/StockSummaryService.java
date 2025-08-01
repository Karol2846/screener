package com.stock.screener.application.service;

import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import com.stock.screener.application.service.event.ApplicationEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockSummaryService {

    private final SeekingAlphaApi seekingAlphaApi;
    private final ApplicationEventPublisher eventPublisher;

    public void processStockSummaries(List<String> symbols) {
        List<StockSummaryCommand> summaries = seekingAlphaApi.getStockSummaries(symbols);
        //TODO: add fundamentalData listener
        summaries.forEach(summary ->
                eventPublisher.publishEvent(ApplicationEvent.of(summary)));
    }
}
