package com.stock.screener.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.stock.screener.domain.model.CompoundId;
import com.stock.screener.domain.model.PriceHistory;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = SPRING)
public interface PriceHistoryMapper {

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget PriceHistory priceHistory, CurrentPriceCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "averagePrice50Days", source = "average50Days")
    @Mapping(target = "averagePrice100Days", source = "average100Days")
    @Mapping(target = "averagePrice200Days", source = "average200Days")
    void update(@MappingTarget PriceHistory priceHistory, MovingAveragesCommand command);

    @Mapping(target = "id", source = "ticker", qualifiedByName = "mapSymbol")
    PriceHistory from(CurrentPriceCommand command);

    @Mapping(target = "id", source = "ticker", qualifiedByName = "mapSymbol")
    @Mapping(target = "averagePrice50Days", source = "average50Days")
    @Mapping(target = "averagePrice100Days", source = "average100Days")
    @Mapping(target = "averagePrice200Days", source = "average200Days")
    PriceHistory from(MovingAveragesCommand command);

    @Named("mapSymbol")
    default CompoundId map(String ticker) {
        return CompoundId.builder()
                .symbol(ticker)
                .build();
    }
}
