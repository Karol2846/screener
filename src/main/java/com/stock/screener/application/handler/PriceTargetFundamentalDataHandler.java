package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.PriceTargetEvent;
import com.stock.screener.application.port.command.PriceTargetCommand;
import com.stock.screener.application.port.out.FundamentalDataRepository;
import com.stock.screener.domain.factory.FundamentalDataFactory;
import com.stock.screener.domain.model.FundamentalData;
import com.stock.screener.domain.service.StockIdentifierMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceTargetFundamentalDataHandler implements DomainEventHandler<PriceTargetEvent> {

    private final StockIdentifierMappingService mappingService;
    private final FundamentalDataFactory factory;
    private final FundamentalDataRepository repository;

    @Override
    public void handle(PriceTargetEvent event) {
        PriceTargetCommand command = event.payload();
        String ticker = mappingService.getSymbolByTickerId(command.tickerId()).get();

        log.info("Updating price target for symbol: {}", ticker);
        repository.findById(forSymbolWithActualDate(ticker)).ifPresentOrElse(
                fundamentalData -> updateFundamentalData(fundamentalData, command),
                () -> createNewFundamentalData(command, ticker));
    }

    private void createNewFundamentalData(PriceTargetCommand command, String ticker) {
        log.debug("FundamentalData for symbol: {} not found, creating new entity from price target: {}", ticker, command);
        repository.save(factory.from(command, ticker));
    }

    private void updateFundamentalData(FundamentalData fundamentalData, PriceTargetCommand command) {
        log.debug("Updating price target: {} for funtamental data with symbol: {}", command, fundamentalData.getId().getSymbol());
        factory.update(fundamentalData, command);
        repository.save(fundamentalData);
    }
}
