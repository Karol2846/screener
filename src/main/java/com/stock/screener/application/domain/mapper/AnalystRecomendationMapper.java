package com.stock.screener.application.domain.mapper;

import com.stock.screener.application.domain.model.AnalystRecommendation;
import com.stock.screener.application.domain.model.CompoundId;
import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AnalystRecomendationMapper {

    default AnalystRecommendation fromStock(Stock stock) {
        return AnalystRecommendation.builder()
                .id(CompoundId.builder()
                        .symbol(stock.getSymbol())
                        .build())
                .build();
    }

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AnalystRecommendation recommendation, AnalystRecommendationCommand command);
}
