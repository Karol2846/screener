package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.port.out.PriceHistoryRepository;
import com.stock.screener.application.service.event.ScreenerApplicationEvent;
import com.stock.screener.domain.mapper.PriceHistoryMapper;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovingAveragesHandler {

    private final PriceHistoryMapper priceMapper;
    private final PriceHistoryRepository priceRepository;

    @EventListener
    public void updateMovingAverage(ScreenerApplicationEvent<MovingAveragesCommand> event) {
        MovingAveragesCommand command = event.payload();
        log.info("Updating price history for symbol: {}", command.ticker());

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
