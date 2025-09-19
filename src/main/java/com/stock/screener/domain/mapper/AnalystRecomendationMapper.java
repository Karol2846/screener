package com.stock.screener.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.stock.screener.domain.model.AnalystRecommendation;
import com.stock.screener.application.port.command.AnalystRecommendationCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface AnalystRecomendationMapper {

    AnalystRecommendation from(AnalystRecommendationCommand command);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AnalystRecommendation recommendation, AnalystRecommendationCommand command);
}
