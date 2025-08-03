package com.stock.screener.application.handler;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.StockSummaryEvent;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.out.StockRepository;
import com.stock.screener.domain.mapper.StockMapper;
import com.stock.screener.domain.model.Stock;
import com.stock.screener.domain.service.StockIdentifierMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockCreationEventHandler implements DomainEventHandler<StockSummaryEvent> {

    private final StockRepository stockRepository;
    private final StockIdentifierMappingService mappingService;
    private final StockMapper stockMapper;

    @Async
    @EventListener
    @Transactional
    public void handle(StockSummaryEvent event) {
        StockSummaryCommand summary = event.payload();

        stockRepository.findById(summary.ticker()).ifPresentOrElse(
                stock ->
                        log.info("Stock for ticker: {} already exists, skipping creation", summary.ticker()),
                () ->
                        createStock(summary));
    }

    private void createStock(StockSummaryCommand summary) {
        log.info("Creating new stock for ticker: {}", summary.ticker());

        Stock stock = stockMapper.mapToStock(summary);
        stockRepository.save(stock);
        mappingService.updateMapping(summary.ticker(), summary.tickerId());

        log.info("Stock for ticker: {} created", summary.ticker());
    }
}