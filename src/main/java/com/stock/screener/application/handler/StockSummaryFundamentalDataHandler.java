package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.StockSummaryEvent;
import com.stock.screener.application.port.command.StockSummaryCommand;
import com.stock.screener.application.port.out.FundamentalDataRepository;
import com.stock.screener.domain.factory.FundamentalDataFactory;
import com.stock.screener.domain.model.FundamentalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockSummaryFundamentalDataHandler implements DomainEventHandler<StockSummaryEvent> {

    private final FundamentalDataFactory factory;
    private final FundamentalDataRepository repository;


    @Override
    public void handle(StockSummaryEvent event) {
        StockSummaryCommand command = event.payload();
        log.info("Updating fundamental data summary for symbol: {}", command.ticker());

        repository.findById(forSymbolWithActualDate(command.ticker())).ifPresentOrElse(
                fundamentalData -> updateFundamentalData(fundamentalData, command),
                () -> createNewFundamentalData(command));
    }

    private void createNewFundamentalData(StockSummaryCommand command) {
        log.debug("FundamentalData for symbol: {} not found, creating new entity from stock summary: {}", command.ticker(), command);
        repository.save(factory.from(command));
    }

    private void updateFundamentalData(FundamentalData fundamentalData, StockSummaryCommand command) {
        log.debug("Updating summary: {} for funtamental data with symbol: {}", command, fundamentalData.getId().getSymbol());
        factory.update(fundamentalData, command);
        repository.save(fundamentalData);
    }
}
