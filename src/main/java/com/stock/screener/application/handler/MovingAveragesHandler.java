package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.MovingAveragesEvent;
import com.stock.screener.application.port.out.PriceHistoryRepository;
import com.stock.screener.domain.mapper.PriceHistoryMapper;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovingAveragesHandler implements DomainEventHandler<MovingAveragesEvent> {

    private final PriceHistoryMapper priceMapper;
    private final PriceHistoryRepository priceRepository;

    @Override
    public void handle(MovingAveragesEvent event) {
        MovingAveragesCommand command = event.payload();
        log.info("Updating moving averages for symbol: {}", command.ticker());

        priceRepository.findById(forSymbolWithActualDate(command.ticker())).ifPresentOrElse(
                price -> updateMovingAverage(price, command),
                () -> createNewPriceHistory(command));
    }

    private void createNewPriceHistory(MovingAveragesCommand command) {
        log.debug("PriceHistory for symbol: {} not found, creating new entity", command.ticker());

        priceRepository.save(priceMapper.from(command));
    }

    private void updateMovingAverage(PriceHistory priceHistory, MovingAveragesCommand command) {
        log.debug("Updating moving averages: {} for symbol: {}", command.movingAverages(), command.ticker());
        priceMapper.update(priceHistory, command);
        priceRepository.save(priceHistory);
    }
}
