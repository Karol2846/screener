package com.stock.screener.application.service;

import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import com.stock.screener.application.port.in.api.FinHubApi;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import com.stock.screener.application.service.event.ApplicationEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final SeekingAlphaApi seekingAlphaApi;
    private final FinHubApi finHubApi;
    private final ApplicationEventPublisher eventPublisher;

    public void processPrice(List<String> symbols) {
        processCurrentPrice(symbols);
        processMovingAverages(symbols);
    }

    private void processCurrentPrice(List<String> symbols) {
        List<CurrentPriceCommand> currentPrices = symbols.stream()
                .map(finHubApi::getCurrentPrice)
                .toList();

        currentPrices.forEach(event ->
                eventPublisher.publishEvent(ApplicationEvent.of(event)));
    }

    private void processMovingAverages(List<String> symbols) {
        List<MovingAveragesCommand> movingAverages = seekingAlphaApi.getMovingAverages(symbols);

        movingAverages.forEach(event ->
                eventPublisher.publishEvent(ApplicationEvent.of(event)));
    }
}
