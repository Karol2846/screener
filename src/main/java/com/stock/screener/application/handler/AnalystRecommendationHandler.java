package com.stock.screener.application.handler;

import com.stock.screener.application.event.DomainEventHandler;
import com.stock.screener.application.event.model.AnalystRecomendationEvent;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.application.port.out.AnalystRecomendationRepository;
import com.stock.screener.domain.factory.AnalystRecomendationFactory;
import com.stock.screener.domain.model.AnalystRecommendation;
import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.service.StockIdentifierMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalystRecommendationHandler implements DomainEventHandler<AnalystRecomendationEvent> {

    private final AnalystRecomendationRepository repository;
    private final StockIdentifierMappingService mappingService;
    private final AnalystRecomendationFactory factory;

    @Override
    public void handle(AnalystRecomendationEvent event) {
        var command = event.payload();
        String ticker = mappingService.getSymbolByTickerId(command.tickerId()).get();

        log.info("Updating analyst recomendation for symbol: {}", ticker);
        repository.findById(CompoundId.forSymbolWithActualDate(ticker)).ifPresentOrElse(
                analystRecommendation -> updateAnalystRecomendation(analystRecommendation, command),
                () -> createNewAnalystRecomendation(command, ticker)
        );
    }

    private void createNewAnalystRecomendation(AnalystRecommendationCommand command, String ticker) {
        log.debug("AnalystRecommendation for symbol: {} not found, creating new entity", ticker);
        repository.save(factory.from(command, ticker));
    }

    private void updateAnalystRecomendation(AnalystRecommendation analystRecommendation, AnalystRecommendationCommand command) {
        log.debug("Updating analyst recomendation: {} for symbol: {}", command, analystRecommendation.getId().getSymbol());
        factory.update(analystRecommendation, command);
        repository.save(analystRecommendation);
    }
}
