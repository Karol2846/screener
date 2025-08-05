package com.stock.screener.application.service;

import com.stock.screener.application.event.DomainEventPublisher;
import com.stock.screener.application.event.model.PriceTargetEvent;
import com.stock.screener.application.port.in.api.SeekingAlphaApi;
import com.stock.screener.domain.service.StockIdentifierMappingService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceTargetService {

    private final SeekingAlphaApi seekingAlphaApi;
    private final StockIdentifierMappingService mappingService;
    private final DomainEventPublisher eventPublisher;

    public void processPriceTargets(List<String> symbolsFromFile) {
        var tickerIds = symbolsFromFile.stream()
                .map(mappingService::getTickerIdBySymbol)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(String::valueOf)
                .toList();

        var priceTargets = seekingAlphaApi.getPriceTarget(tickerIds);

        priceTargets.forEach(target ->
                eventPublisher.publish(new PriceTargetEvent(target)));
    }
}
