package com.stock.screener.application.event.model;

import com.stock.screener.application.port.command.AnalystRecommendationCommand;

public record AnalystRecomendationEvent(AnalystRecommendationCommand payload)
        implements DomainEvent<AnalystRecommendationCommand> {}
