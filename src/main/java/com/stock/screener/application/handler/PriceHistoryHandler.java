package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.CurrentPriceEvent;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.domain.factory.PriceHistoryFactory;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.out.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceHistoryHandler implements DomainEventHandler<CurrentPriceEvent> {

    private final PriceHistoryFactory factory;
    private final PriceHistoryRepository priceRepository;

    @Override
    public void handle(CurrentPriceEvent event) {
        CurrentPriceCommand command = event.payload();
        log.info("Updating current price for symbol: {}", command.ticker());

        priceRepository.findById(forSymbolWithActualDate(command.ticker())).ifPresentOrElse(
                price -> updateCurrentPrice(price, command),
                () -> createNewPriceHistory(command));
    }

    private void createNewPriceHistory(CurrentPriceCommand command) {
        log.debug("PriceHistory for symbol: {} not found, creating new entity", command.ticker());

        priceRepository.save(factory.from(command));
    }

    private void updateCurrentPrice(PriceHistory priceHistory, CurrentPriceCommand command) {
        log.debug("Updating current price: {} for symbol: {}", command.currentPrice(), command.ticker());
        factory.update(priceHistory, command);
        priceRepository.save(priceHistory);
    }
}
