package com.stock.screener.application.handler;

import static com.stock.screener.domain.model.CompoundId.forSymbolWithActualDate;

import com.stock.screener.application.event.model.CurrentPriceEvent;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.domain.mapper.PriceHistoryMapper;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.out.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceHistoryHandler {

    private final PriceHistoryMapper priceMapper;
    private final PriceHistoryRepository priceRepository;

    @Async
    @EventListener
    @Transactional
    public void updateCurrentPrice(CurrentPriceEvent event) {
        CurrentPriceCommand command = event.payload();
        log.info("Updating current price for symbol: {}", command.ticker());

        priceRepository.findById(forSymbolWithActualDate(command.ticker())).ifPresentOrElse(
                price -> updateCurrentPrice(price, command),
                () -> createNewPriceHistory(command));
    }

    private void createNewPriceHistory(CurrentPriceCommand command) {
        log.debug("PriceHistory for symbol: {} not found, creating new entity", command.ticker());

        priceRepository.save(priceMapper.from(command));
    }

    private void updateCurrentPrice(PriceHistory priceHistory, CurrentPriceCommand command) {
        log.debug("Updating current price: {} for symbol: {}", command.currentPrice(), command.ticker());
        priceMapper.update(priceHistory, command);
        priceRepository.save(priceHistory);
    }
}
