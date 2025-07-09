package com.stock.screener.application.domain.mapper;

import com.stock.screener.application.domain.model.Stock;
import com.stock.screener.application.port.command.StockSummaryCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StockMapper {

    @Mapping(target = "symbol", source = "ticker")
    @Mapping(target = "seekingAlphaTrackerId", source = "tickerId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "priceHistory", ignore = true)
    @Mapping(target = "fundamentalData", ignore = true)
    @Mapping(target = "analystRecommendations", ignore = true)
    Stock mapToStock(StockSummaryCommand stockSummaryCommand);
}
