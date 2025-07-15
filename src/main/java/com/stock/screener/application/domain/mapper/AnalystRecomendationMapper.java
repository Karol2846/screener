package com.stock.screener.application.domain.mapper;

import com.stock.screener.application.domain.model.AnalystRecommendation;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AnalystRecomendationMapper {

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AnalystRecommendation recommendation, AnalystRecommendationCommand command);
}
