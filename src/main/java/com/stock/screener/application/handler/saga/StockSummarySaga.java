package com.stock.screener.application.handler.saga;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.StockSummaryEvent;
import com.stock.screener.application.handler.StockCreationEventHandler;
import com.stock.screener.application.handler.StockSummaryFundamentalDataHandler;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockSummarySaga implements DomainEventHandler<StockSummaryEvent> {

    private final StockCreationEventHandler stockCreationHandler;
    private final StockSummaryFundamentalDataHandler fundamentalDataHandler;
    private final TaskExecutor asynctaskExecutor;

    @Override
    public void handle(StockSummaryEvent event) {
        log.info("Starting StockSummary saga for symbol: {}", event.payload().ticker());

        CompletableFuture
                .supplyAsync(() -> {
                    stockCreationHandler.handleSync(event);
                    return event;
                }, asynctaskExecutor)
                .thenAcceptAsync(fundamentalDataHandler::handleSync, asynctaskExecutor)
                .exceptionally(throwable -> {
                    log.error("StockSummary saga failed for symbol: {}", event.payload().ticker(), throwable);
                    return null;
                });
    }
}