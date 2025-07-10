package com.stock.screener.application.domain.mapper;

import com.stock.screener.application.domain.model.CompoundId;
import com.stock.screener.application.domain.model.PriceHistory;
import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.CurrentPriceCommand;
import com.stock.screener.application.port.command.MovingAveragesCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface PriceHistoryMapper {

    default PriceHistory fromStock(Stock stock) {
        return PriceHistory.builder()
                .id(CompoundId.builder()
                        .symbol(stock.getSymbol())
                        .build())
                .build();
    }

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget PriceHistory priceHistory, CurrentPriceCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "averagePrice50Days", source = "average50Days")
    @Mapping(target = "averagePrice100Days", source = "average100Days")
    @Mapping(target = "averagePrice200Days", source = "average200Days")
    void update(@MappingTarget PriceHistory priceHistory, MovingAveragesCommand command);
}
