package com.stock.screener.domain.factory;

import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import com.stock.screener.domain.mapper.AnalystRecomendationMapper;
import com.stock.screener.domain.model.AnalystRecommendation;
import com.stock.screener.domain.model.CompoundId;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class AnalystRecomendationFactory extends AbstractFactory{

    private final AnalystRecomendationMapper mapper;

    public AnalystRecomendationFactory(EntityManager entityManager, AnalystRecomendationMapper mapper) {
        super(entityManager);
        this.mapper = mapper;
    }

    public AnalystRecommendation from(AnalystRecommendationCommand command, String ticker) {
        var analystRecommendation = mapper.from(command);

        analystRecommendation.setStock(getReference(ticker));
        analystRecommendation.setId(CompoundId.forSymbolWithActualDate(ticker));

        return analystRecommendation;
    }

    public void update(AnalystRecommendation analystRecommendation, AnalystRecommendationCommand command) {
        mapper.update(analystRecommendation, command);
    }
}
